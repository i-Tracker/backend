package backend.itracker.tracker.oauth.client

import backend.itracker.tracker.oauth.OauthServerType
import org.springframework.stereotype.Component

@Component
class OauthMemberClientComposite(
    private val clients: Set<OauthMemberClient>
) {

    private val mappers: Map<OauthServerType, OauthMemberClient> = clients.associateBy { it.supports() }

    fun fetch(oauthServerType: OauthServerType, code: String) = mappers[oauthServerType]?.fetch(code)
        ?: throw IllegalStateException("지원하지 않는 소셜 로그인 타입 입니다. oauthServerType=$oauthServerType")
}
