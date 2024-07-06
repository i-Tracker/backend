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
import backend.itracker.tracker.oauth.exception.DuplicatedMemberException
import backend.itracker.tracker.oauth.exception.FirstLoginException
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
    ): Member {
        val fetchMember = oauthMemberClientComposite.fetch(oauthServerType, authCode, redirectType)
        return memberService.findByOauthId(fetchMember.oauthId).getOrNull()
            ?: throw FirstLoginException(message = "첫번째 로그인 대상입니다. member: $fetchMember")
    }

    fun firstLogin(
        oauthServerType: OauthServerType,
        authCode: String,
        redirectType: RedirectType
    ): Member {
        val fetchMember = oauthMemberClientComposite.fetch(oauthServerType, authCode, redirectType)
        if (memberService.findByOauthId(fetchMember.oauthId).isPresent) {
            throw DuplicatedMemberException(message = "이미 가입된 회원입니다. member: $fetchMember")
        }

        return fetchMember
    }

    fun register(
        member: Member
    ) {
        val savedMember = memberService.findByOauthId(member.oauthId).getOrNull()
            ?: memberService.save(member)

        memberService.updateProfile(savedMember.oauthId, member)
    }

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

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return memberService.isDuplicatedPhoneNumber(phoneNumber)
    }
}
