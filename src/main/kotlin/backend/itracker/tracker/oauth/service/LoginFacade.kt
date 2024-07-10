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
        val member = oauthService.fetchMember(oauthServerType, authCode, redirectType)
        oauthService.register(member)

        return oauthService.issueToken(member)
    }
}
