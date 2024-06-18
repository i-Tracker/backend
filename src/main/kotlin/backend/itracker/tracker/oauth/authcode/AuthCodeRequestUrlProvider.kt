package backend.itracker.tracker.oauth.authcode

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType

interface AuthCodeRequestUrlProvider {

    fun supports(): OauthServerType

    fun provide(redirectType: RedirectType): String
}
