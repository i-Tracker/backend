package backend.itracker.tracker.oauth.service

import backend.itracker.tracker.infra.oauth.AuthorizationHeader
import backend.itracker.tracker.infra.oauth.jwt.JwtDecoder
import backend.itracker.tracker.infra.oauth.jwt.JwtEncoder
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.member.service.MemberService
import backend.itracker.tracker.oauth.OauthId
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val memberService: MemberService,
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder
) {

    fun issueToken(
        member: Member
    ): String {
        return createAccessToken(member.oauthId)
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
