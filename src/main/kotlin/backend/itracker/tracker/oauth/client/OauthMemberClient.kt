package backend.itracker.tracker.oauth.client

import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType

interface OauthMemberClient {

    fun supports(): OauthServerType

    fun fetch(code: String, redirectType: RedirectType): Member
}
