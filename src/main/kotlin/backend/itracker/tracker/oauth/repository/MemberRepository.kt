package backend.itracker.tracker.oauth.repository

import backend.itracker.tracker.oauth.Member
import backend.itracker.tracker.oauth.OauthId
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import kotlin.jvm.optionals.getOrNull

fun MemberRepository.getByOauthId(oauthId: OauthId) = findByOauthId(oauthId).getOrNull()
    ?: throw NoSuchElementException("회원을 찾을 수 없습니다. oauthId: $oauthId")

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByOauthId(oauthId: OauthId): Optional<Member>
}
