package backend.itracker.tracker.service.service

import backend.itracker.tracker.service.common.HmacGenerator
import backend.itracker.tracker.service.request.DeepLinkRequest
import backend.itracker.tracker.service.response.CoupangDeepLinkResponse
import backend.itracker.tracker.service.response.Deeplink
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

private const val REQUEST_METHOD = "POST"
private const val DOMAIN = "https://api-gateway.coupang.com"
private const val URL = "/v2/providers/affiliate_open_api/apis/openapi/v1/deeplink"

private const val APPLICATION_JSON = "application/json"
private const val UTF_8 = "UTF-8"
private const val AUTHORIZATION = "Authorization"
private const val CONTENT_TYPE = "Content-Type"
private const val CONTENT_ENCODING = "Content-Encoding"

@Service
class CoupangApiClient(
    val hmacGenerator: HmacGenerator,
    val restTemplate: RestTemplate
) {

    @Value("\${coupang.access-key}")
    private lateinit var accessKey: String

    @Value("\${coupang.secret-key}")
    private lateinit var secretKey: String

    fun issueDeepLinks(originLinks: List<String>): List<Deeplink> {
        val authorization = hmacGenerator.generate(REQUEST_METHOD, URL, secretKey, accessKey)
        val httpHeaders = HttpHeaders().apply {
            set(AUTHORIZATION, authorization)
            set(CONTENT_TYPE, APPLICATION_JSON)
            set(CONTENT_ENCODING, UTF_8)
        }
        val body = ObjectMapper().writeValueAsString(DeepLinkRequest(originLinks))

        val request = HttpEntity(body, httpHeaders)
        val response = restTemplate.postForEntity("$DOMAIN$URL", request, CoupangDeepLinkResponse::class.java)
        return response.body?.data ?: throw IllegalStateException(
            """Deeplink가 생성중에 오류가 발생했습니다. 
            |rCode : ${response.body?.rCode}
            |rMessage : ${response.body?.rMessage}""".trimMargin()
        )
    }
}

