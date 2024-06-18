package backend.itracker.tracker.infra.oauth.kakao

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.kakao")
data class KakaoOauthConfig(
    val redirectUri: String,
    val localRedirectUri: String,
    val clientId: String,
    val clientSecret: String,
    val scope: MutableList<String>
)
