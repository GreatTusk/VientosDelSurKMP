package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatus
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.RoomCleaningType
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.suspendTransaction
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.housekeeping.RoomRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.Room
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomCleaningStatus
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomState
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll

internal object DBRoomRepository : RoomRepository {
    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> = suspendTransaction {
        return@suspendTransaction try {
            Result.Success(RoomEntity.all().map { it.toRoom() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomState>, DataError.Remote> = suspendTransaction {
        return@suspendTransaction try {
            val rooms = HousekeeperShiftRoomTable
                .innerJoin(RoomTable)
                .innerJoin(WorkShiftTable)
                .selectAll()
                .where { (WorkShiftTable.employeeId eq housekeeperId) and (WorkShiftTable.date eq date) }
                .map { row ->
                    val roomId = row[RoomTable.id].value
                    val room = RoomEntity[roomId].toRoom()

                    val latestStatus = RoomStatusTable
                        .selectAll()
                        .where { RoomStatusTable.roomId eq roomId }
                        .orderBy(RoomStatusTable.updatedAt to SortOrder.DESC)
                        .firstOrNull()

                    RoomState(
                        room = room,
                        roomCleaningStatus = latestStatus?.let {
                            val status = it[RoomStatusTable.roomStatus]
                            val updatedAt = it[RoomStatusTable.updatedAt]

                            when (status) {
                                RoomStatus.PENDING -> RoomCleaningStatus.Pending
                                RoomStatus.IN_CLEANING -> RoomCleaningStatus.InCleaning(updatedAt)
                                RoomStatus.IN_REVISION -> RoomCleaningStatus.InRevision(updatedAt)
                                RoomStatus.DONE -> RoomCleaningStatus.Done(updatedAt)
                            }
                        } ?: RoomCleaningStatus.Pending,
                        roomCleaningType = when (row[HousekeeperShiftRoomTable.roomCleaningType]) {
                            RoomCleaningType.ROOM -> com.portafolio.vientosdelsur.domain.housekeeping.model.RoomCleaningType.ROOM
                            RoomCleaningType.GUEST -> com.portafolio.vientosdelsur.domain.housekeeping.model.RoomCleaningType.GUEST
                        }
                    )
                }
            Result.Success(rooms)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getAllRoomStatusOn(date: LocalDate): Result<List<RoomState>, DataError.Remote> =
        suspendTransaction {
            TODO("Not yet implemented")
        }
}