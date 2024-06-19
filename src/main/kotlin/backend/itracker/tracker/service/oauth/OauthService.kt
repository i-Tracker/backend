package backend.itracker.tracker.service.oauth

import backend.itracker.tracker.infra.oauth.jwt.JwtEncoder
import backend.itracker.tracker.oauth.OauthId
import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.oauth.authcode.AuthCodeRequestUrlProviderComposite
import backend.itracker.tracker.oauth.client.OauthMemberClientComposite
import backend.itracker.tracker.service.MemberService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class OauthService(
    private val authCodeRequestUrlProviderComposite: AuthCodeRequestUrlProviderComposite,
    private val oauthMemberClientComposite: OauthMemberClientComposite,
    private val memberService: MemberService,
    private val jwtEncoder: JwtEncoder
) {

    fun getAuthCodeRequestUrl(oauthServerType: OauthServerType, redirectType: RedirectType): String {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType, redirectType)
    }

    fun login(
        oauthServerType: OauthServerType,
        authCode: String,
        redirectType: RedirectType
    ): String {
        val fetchMember = oauthMemberClientComposite.fetch(oauthServerType, authCode, redirectType)
        val savedMember = memberService.findByOauthId(fetchMember.oauthId).getOrNull()
            ?: memberService.save(fetchMember)

        memberService.updateProfile(savedMember.oauthId, fetchMember)

        return createAccessToken(savedMember.oauthId)
    }

    private fun createAccessToken(oauthId: OauthId): String {
        return jwtEncoder.issueJwtToken(
            mapOf(
                "serverId" to oauthId.oauthServerId,
                "type" to oauthId.oauthServerType.name
            )
        )
    }
}
