package backend.itracker.tracker.service.oauth

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.authcode.AuthCodeRequestUrlProviderComposite
import backend.itracker.tracker.oauth.client.OauthMemberClientComposite
import backend.itracker.tracker.oauth.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class OauthService(
    private val authCodeRequestUrlProviderComposite: AuthCodeRequestUrlProviderComposite,
    private val oauthMemberClientComposite: OauthMemberClientComposite,
    private val memberRepository: MemberRepository
) {

    fun getAuthCodeRequestUrl(oauthServerType: OauthServerType): String {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType)
    }

    fun login(
        oauthServerType: OauthServerType,
        authCode: String
    ): Long {
        val member = oauthMemberClientComposite.fetch(oauthServerType, authCode)
        val savedMember = memberRepository.findByOauthId(member.oauthId)
            .orElseGet { memberRepository.save(member) }

        return savedMember.id
    }
}
