package backend.itracker.tracker.oauth.authcode

import backend.itracker.tracker.oauth.OauthServerType
import org.springframework.stereotype.Component

@Component
class AuthCodeRequestUrlProviderComposite(
    private val providers: Set<AuthCodeRequestUrlProvider>
) {

    private val mappers: Map<OauthServerType, AuthCodeRequestUrlProvider> = providers.associateBy { it.supports() }

    fun provide(oauthServerType: OauthServerType): String = mappers[oauthServerType]?.provide()
        ?: throw IllegalStateException("지원하지 않는 OauthServerType 입니다. oauthServerType=$oauthServerType")
}
