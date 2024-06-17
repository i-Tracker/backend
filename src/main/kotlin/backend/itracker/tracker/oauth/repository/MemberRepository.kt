package backend.itracker.tracker.oauth.repository

import backend.itracker.tracker.oauth.Member
import backend.itracker.tracker.oauth.OauthId
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByOauthId(oauthId: OauthId): Optional<Member>
}
