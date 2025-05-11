package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatus
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.suspendTransaction
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.housekeeping.RoomRepository
import com.portafolio.vientosdelsur.shared.domain.Room
import com.portafolio.vientosdelsur.shared.domain.RoomCleaningStatus
import com.portafolio.vientosdelsur.shared.domain.RoomState
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.mapLazy
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
            val workShiftId = WorkShiftEntity.find {
                (WorkShiftTable.employeeId eq housekeeperId) and
                        (WorkShiftTable.date eq date)
            }.mapLazy { it.id.value }.firstOrNull() ?: return@suspendTransaction Result.Empty

            val rooms = HousekeeperShiftRoomTable
                .innerJoin(RoomTable)
                .selectAll()
                .where { HousekeeperShiftRoomTable.wordShiftId eq workShiftId }
                .map { row ->
                    val roomId = row[RoomTable.id].value
                    val room = RoomEntity[roomId].toRoom()

                    val latestStatus = RoomStatusTable
                        .selectAll()
                        .where { RoomStatusTable.roomId eq roomId }
                        .orderBy(RoomStatusTable.updatedAt to SortOrder.DESC)
                        .limit(1)
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
                        roomCleaningType = row[HousekeeperShiftRoomTable.roomCleaningType]
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