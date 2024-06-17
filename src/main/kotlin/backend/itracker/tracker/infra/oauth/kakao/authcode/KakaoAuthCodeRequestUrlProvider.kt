package backend.itracker.tracker.infra.oauth.kakao.authcode

import backend.itracker.tracker.infra.oauth.kakao.KakaoOauthConfig
import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.authcode.AuthCodeRequestUrlProvider
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class KakaoAuthCodeRequestUrlProvider(
    private val kakaoOauthConfig: KakaoOauthConfig
) : AuthCodeRequestUrlProvider {

    override fun supports(): OauthServerType {
        return OauthServerType.KAKAO
    }

    override fun provide(): String {
        return UriComponentsBuilder
            .fromUriString("https://kauth.kakao.com/oauth/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", kakaoOauthConfig.clientId)
            .queryParam("redirect_uri", kakaoOauthConfig.redirectUri)
            .queryParam("scope", kakaoOauthConfig.scope.joinToString { "," })
            .toUriString()
    }
}
