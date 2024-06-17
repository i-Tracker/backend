package backend.itracker.tracker.oauth

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "member",
    uniqueConstraints = [
        UniqueConstraint(name = "oauth_id_unique", columnNames = ["oauth_server_id", "oauth_server_type"])
    ]
)
class Member(

    @Embedded
    val oauthId: OauthId,

    val nickname: String,

    @Enumerated(EnumType.STRING)
    val authType: AuthType = AuthType.USER,

    id: Long = 0L
) : BaseEntity(id) {

    override fun toString(): String {
        return "Member(oauthId=$oauthId, nickname='$nickname', authType=$authType)"
    }
}
