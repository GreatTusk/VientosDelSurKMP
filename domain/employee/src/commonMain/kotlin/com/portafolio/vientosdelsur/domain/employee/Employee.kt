package com.portafolio.vientosdelsur.domain.employee

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class Employee(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val occupation: Occupation,
    val userId: String,
    val email: String,
    val photoUrl: String?,
    val phoneNumber: String,
    val isEnabled: Boolean
)

data class CreateEmployee(
    val firstName: String,
    val lastName: String,
    val occupation: Occupation,
    val userId: String,
    val email: String,
    val hireDate: LocalDate,
    val dayOff: DayOfWeek,
    val uploadPhoto: UploadPhoto?
)

sealed interface UploadPhoto {
    data class URL(val url: String) : UploadPhoto
    data class Raw(val byteArray: ByteArray) : UploadPhoto {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as Raw
            return byteArray.contentEquals(other.byteArray)
        }

        override fun hashCode(): Int {
            return byteArray.contentHashCode()
        }
    }
}