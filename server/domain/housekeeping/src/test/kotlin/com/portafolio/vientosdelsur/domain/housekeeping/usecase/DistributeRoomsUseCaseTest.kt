package com.portafolio.vientosdelsur.domain.housekeeping.usecase

import com.portafolio.vientosdelsur.data.FakeHousekeeperShiftRepository
import com.portafolio.vientosdelsur.data.FakeRoomBookingRepository
import com.portafolio.vientosdelsur.data.FakeRoomRepository
import com.portafolio.vientosdelsur.domain.employee.Floor
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.room.RoomBookingRepository
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.domain.room.model.*
import kotlinx.coroutines.test.StandardTestDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.test.*

class DistributeRoomsUseCaseTest : KoinComponent {
    private lateinit var useCase: DistributeRoomsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    singleOf(::FakeRoomRepository).bind<RoomRepository>()
                    singleOf(::FakeRoomBookingRepository).bind<RoomBookingRepository>()
                    singleOf(::FakeHousekeeperShiftRepository).bind<HousekeeperShiftRepository>()
                    factoryOf(::DistributeRoomsUseCase)
                }
            )
        }
        useCase = inject<DistributeRoomsUseCase>().value
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun `chooses preferred room when upper floor is preferred and rooms are available`() {
        val floor4 = Floor(4)
        val rooms = setOf(
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 412
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 112
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            )
        )

        val foundRoom = useCase.findPreferredRoom(floor4, rooms)
        assertEquals(rooms.first(), foundRoom)
    }

    @Test
    fun `chooses closest room when upper floor is preferred but rooms not available`() {
        val floor4 = Floor(4)
        val rooms = setOf(
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 312
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 112
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            )
        )

        val foundRoom = useCase.findPreferredRoom(floor4, rooms)
        assertEquals(rooms.first(), foundRoom)
    }

    @Test
    fun `chooses preferred room when lower floor is preferred and rooms are available`() {
        val floor4 = Floor(1)
        val rooms = setOf(
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 112
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 412
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            )
        )

        val foundRoom = useCase.findPreferredRoom(floor4, rooms)
        assertEquals(rooms.first(), foundRoom)
    }

    @Test
    fun `chooses closest room when lower floor is preferred but rooms not available`() {
        val floor4 = Floor(1)
        val rooms = setOf(
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 212
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 412
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            )
        )

        val foundRoom = useCase.findPreferredRoom(floor4, rooms)
        assertEquals(rooms.first(), foundRoom)
    }

    @Test
    fun `chooses any room when there is no preference`() {
        val floor4 = null
        val rooms = setOf(
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 212
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 412
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 312
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = Room(
                    1, RoomTypeDetails(
                        RoomType.TRIPLE,
                        workUnit = 1,
                        checkOutWorkUnit = 2
                    ),
                    number = 212
                ),
                workUnits = 1,
                cleaningType = RoomCleaningType.ROOM
            )
        )

        val foundRoom = useCase.findPreferredRoom(floor4, rooms)
        assertTrue(foundRoom in rooms)
    }
}
