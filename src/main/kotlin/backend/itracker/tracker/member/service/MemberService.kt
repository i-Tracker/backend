package backend.itracker.tracker.member.service

import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.oauth.OauthId
import backend.itracker.tracker.oauth.repository.MemberRepository
import backend.itracker.tracker.oauth.repository.getByOauthId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun save(member: Member): Member {
        return memberRepository.save(member)
    }

    fun updateProfile(oauthId: OauthId, target: Member) {
        val findMember = memberRepository.getByOauthId(oauthId)
        findMember.updateProfile(target)
    }

    @Transactional(readOnly = true)
    fun findByOauthId(oauthId: OauthId): Optional<Member> {
        return memberRepository.findByOauthId(oauthId)
    }

    @Transactional(readOnly = true)
    fun getByOauthId(oauthId: OauthId): Member {
        return memberRepository.getByOauthId(oauthId)
    }
}
