package backend.itracker.tracker.oauth.authcode

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import org.springframework.stereotype.Component

@Component
class AuthCodeRequestUrlProviderComposite(
    private val providers: Set<AuthCodeRequestUrlProvider>
) {

    private val mappers: Map<OauthServerType, AuthCodeRequestUrlProvider> = providers.associateBy { it.supports() }

    fun provide(oauthServerType: OauthServerType, redirectType: RedirectType): String = mappers[oauthServerType]?.provide(redirectType)
        ?: throw IllegalStateException("지원하지 않는 OauthServerType 입니다. oauthServerType=$oauthServerType")
}
