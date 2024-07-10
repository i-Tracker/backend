package backend.itracker.tracker.oauth.service

import backend.itracker.tracker.infra.oauth.AuthorizationHeader
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.member.service.MemberService
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
    private val tokenService: TokenService,
) {
    fun getAuthCodeRequestUrl(oauthServerType: OauthServerType, redirectType: RedirectType): String {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType, redirectType)
    }

    fun fetchMember(
        oauthServerType: OauthServerType,
        authCode: String,
        redirectType: RedirectType
    ): Member {
        return oauthMemberClientComposite.fetch(oauthServerType, authCode, redirectType)
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
        return tokenService.issueToken(member)
    }

    fun findByOauthId(authorizationHeader: AuthorizationHeader): Member {
        return tokenService.findByOauthId(authorizationHeader)
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return memberService.isDuplicatedPhoneNumber(phoneNumber)
    }
}
