package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.booking.RoomBookingTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.domain.housekeeping.RoomBookingRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomBookingId
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

internal object DBRoomBookingRepository : RoomBookingRepository {
    override suspend fun getBookedRoomsOn(date: LocalDate): Result<List<RoomBookingId>, DataError.Remote> =
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
                    RoomBookingId(row[RoomTable.id].value, row[workUnitsColumn])
                }
        }
}