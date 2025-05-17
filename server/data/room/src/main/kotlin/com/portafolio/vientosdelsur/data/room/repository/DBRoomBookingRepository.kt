package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.booking.RoomBookingEntity
import com.portafolio.vientosdelsur.core.database.entity.booking.RoomBookingTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.housekeeping.RoomBookingRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomBooking
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

internal object DBRoomBookingRepository : RoomBookingRepository {
    override suspend fun getBookedRoomsOn(date: LocalDate): Result<List<RoomBooking>, DataError.Remote> =
        safeSuspendTransaction {
            val workUnitsColumn = Case()
                .When(RoomBookingTable.endDate eq date, RoomTypeTable.checkOutWorkUnit)
                .Else(RoomTypeTable.workUnit).alias("workUnits")

            RoomBookingTable.innerJoin(RoomTable)
                .innerJoin(RoomTypeTable)
                .select(
                    RoomTable.id,
                    workUnitsColumn,
                ).map { row ->
                    val roomId = row[RoomTable.id].value
                    val room = RoomEntity[roomId].toRoom()
                    val workUnits = row[workUnitsColumn]
                    RoomBooking(
                        room = room,
                        workUnits = workUnits
                    )
                }
        }


}