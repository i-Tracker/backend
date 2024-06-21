package backend.itracker.tracker.oauth.service

import backend.itracker.tracker.infra.oauth.AuthorizationHeader
import backend.itracker.tracker.infra.oauth.jwt.JwtDecoder
import backend.itracker.tracker.infra.oauth.jwt.JwtEncoder
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.member.service.MemberService
import backend.itracker.tracker.oauth.OauthId
import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.oauth.authcode.AuthCodeRequestUrlProviderComposite
import backend.itracker.tracker.oauth.client.OauthMemberClientComposite
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class OauthService(
    private val authCodeRequestUrlProviderComposite: AuthCodeRequestUrlProviderComposite,
    private val oauthMemberClientComposite: OauthMemberClientComposite,
    private val memberService: MemberService,
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder
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

    fun findByOauthId(authorizationHeader: AuthorizationHeader): Member {
        val oauthId = jwtDecoder.parseOauthId(authorizationHeader)
        return memberService.getByOauthId(oauthId)
    }
}
