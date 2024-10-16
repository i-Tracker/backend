package backend.itracker.tracker.infra.oauth.kakao

import backend.itracker.tracker.infra.oauth.kakao.client.KakaoApiClient
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.oauth.client.OauthMemberClient
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@Component
class KakaoMemberClient(
    private val kakaoApiClient: KakaoApiClient,
    private val kakaoOauthConfig: KakaoOauthConfig
) : OauthMemberClient {

    override fun supports(): OauthServerType {
        return OauthServerType.KAKAO
    }

    override fun fetch(code: String, redirectType: RedirectType): Member {
        val kakaoToken = kakaoApiClient.fetchToken(tokenRequestParams(code, redirectType))

        return kakaoApiClient.fetchMember("Bearer ${kakaoToken.accessToken}")
            .toDomain()
    }

    private fun tokenRequestParams(authCode: String, redirectType: RedirectType): MultiValueMap<String, String> {
        if (redirectType == RedirectType.LOCAL) {
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.add("grant_type", "authorization_code")
            params.add("client_id", kakaoOauthConfig.clientId)
            params.add("redirect_uri", kakaoOauthConfig.localRedirectUri)
            params.add("code", authCode)
            params.add("client_secret", kakaoOauthConfig.clientSecret)
            return params
        }

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", kakaoOauthConfig.clientId)
        params.add("redirect_uri", kakaoOauthConfig.redirectUri)
        params.add("code", authCode)
        params.add("client_secret", kakaoOauthConfig.clientSecret)
        return params
    }
}
