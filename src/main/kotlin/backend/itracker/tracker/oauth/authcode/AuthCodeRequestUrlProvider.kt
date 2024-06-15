package backend.itracker.tracker.oauth.authcode

import backend.itracker.tracker.oauth.OauthServerType

interface AuthCodeRequestUrlProvider {

    fun supports(): OauthServerType

    fun provide(): String
}
