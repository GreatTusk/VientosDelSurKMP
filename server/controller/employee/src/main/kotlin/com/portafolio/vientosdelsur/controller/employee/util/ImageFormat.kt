package com.portafolio.vientosdelsur.controller.employee.util

enum class ImageFormat(val signature: ByteArray, val offset: Int = 0) {
    JPEG(byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte())),
    PNG(byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A)),
    GIF87A("GIF87a".toByteArray()),
    GIF89A("GIF89a".toByteArray()),
    BMP(byteArrayOf(0x42, 0x4D)),
    WEBP(byteArrayOf(0x57, 0x45, 0x42, 0x50), 8), // WEBP signature starts at offset 8
    TIFF_LE(byteArrayOf(0x49, 0x49, 0x2A, 0x00)), // Little endian
    TIFF_BE(byteArrayOf(0x4D, 0x4D, 0x00, 0x2A))  // Big endian
}

private fun detectImageFormat(byteArray: ByteArray): ImageFormat? {
    for (format in ImageFormat.entries) {
        if (matchesSignature(byteArray, format)) {
            return format
        }
    }
    return null
}

fun ByteArray.isImage(): Boolean {
    return detectImageFormat(this) != null
}

private fun matchesSignature(byteArray: ByteArray, format: ImageFormat): Boolean {
    val signature = format.signature
    val offset = format.offset

    if (byteArray.size < signature.size + offset) return false

    for (i in signature.indices) {
        if (byteArray[i + offset] != signature[i]) {
            return false
        }
    }
    return true
}
