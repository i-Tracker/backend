package backend.itracker.tracker.member.domain.repository

import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.oauth.OauthId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import kotlin.jvm.optionals.getOrNull

fun MemberRepository.getByOauthId(oauthId: OauthId) = findByOauthId(oauthId).getOrNull()
    ?: throw NoSuchElementException("회원을 찾을 수 없습니다. oauthId: $oauthId")

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByOauthId(oauthId: OauthId): Optional<Member>

    @Query(
        """
            select count(m) > 0
            from Member m
            where m.phoneNumber = :phoneNumber
        """
    )
    fun isDuplicatedPhoneNumber(@Param("phoneNumber") phoneNumber: String): Boolean
}
