package backend.itracker.tracker.oauth.client

import backend.itracker.tracker.oauth.Member
import backend.itracker.tracker.oauth.OauthServerType

interface OauthMemberClient {

    fun supports(): OauthServerType

    fun fetch(code: String): Member
}
