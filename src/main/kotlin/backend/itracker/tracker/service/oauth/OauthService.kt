package backend.itracker.tracker.service.oauth

import backend.itracker.tracker.infra.oauth.jwt.JwtEncoder
import backend.itracker.tracker.oauth.OauthId
import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.oauth.authcode.AuthCodeRequestUrlProviderComposite
import backend.itracker.tracker.oauth.client.OauthMemberClientComposite
import backend.itracker.tracker.oauth.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class OauthService(
    private val authCodeRequestUrlProviderComposite: AuthCodeRequestUrlProviderComposite,
    private val oauthMemberClientComposite: OauthMemberClientComposite,
    private val memberRepository: MemberRepository,
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
        val member = oauthMemberClientComposite.fetch(oauthServerType, authCode, redirectType)
        val savedMember = memberRepository.findByOauthId(member.oauthId)
            .orElseGet { memberRepository.save(member) }

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
