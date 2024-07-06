package backend.itracker.tracker.oauth.service

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import org.springframework.stereotype.Service

@Service
class LoginFacade(
    private val oauthService: OauthService
) {

    fun login(
        oauthServerType: OauthServerType,
        authCode: String,
        redirectType: RedirectType
    ): String {
        val member = oauthService.login(oauthServerType, authCode, RedirectType.LOCAL)

        return oauthService.issueToken(member)
    }

    fun firstLogin(
        oauthServerType: OauthServerType,
        authCode: String,
        redirectType: RedirectType
    ): String {
        val member = oauthService.firstLogin(oauthServerType, authCode, RedirectType.LOCAL)
        oauthService.register(member)

        return oauthService.issueToken(member)
    }
}
