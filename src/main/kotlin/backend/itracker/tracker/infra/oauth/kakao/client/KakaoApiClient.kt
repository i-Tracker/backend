package backend.itracker.tracker.infra.oauth.kakao.client

import backend.itracker.tracker.infra.oauth.kakao.dto.KakaoMemberResponse
import backend.itracker.tracker.infra.oauth.kakao.dto.KakaoToken
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

interface KakaoApiClient {

    @PostExchange(url = "https://kauth.kakao.com/oauth/token", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    fun fetchToken(@RequestParam params: MultiValueMap<String, String>): KakaoToken

    @GetExchange(url = "https://kapi.kakao.com/v2/user/me")
    fun fetchMember(@RequestHeader(name=HttpHeaders.AUTHORIZATION) beaererToken: String): KakaoMemberResponse
}
