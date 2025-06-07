package com.f776.japanesedictionary.data.imageanalysis.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.network.safeCall
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisService
import com.portafolio.vientosdelsur.network.BuildConfig
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

internal class KtorImageAnalysisService(private val httpClient: HttpClient) : ImageAnalysisService {
    override suspend fun classifyImage(byteArray: ByteArray): Result<ImageAnalysisResult, DataError.Remote> =
        safeCall<ImageAnalysisResult> {
            httpClient.post("${BuildConfig.BASE_URL}/image-analysis") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                key = "room-photo",
                                value = byteArray,
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, "image/png")
                                    append(HttpHeaders.ContentDisposition, "filename=\"room-photo.png\"")
                                }
                            )
                        }
                    )
                )
            }
        }
}