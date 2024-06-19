package backend.itracker.tracker.oauth

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Column
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

    var nickname: String,

    @Column(columnDefinition = "text")
    var profileImage: String,

    @Enumerated(EnumType.STRING)
    val authType: AuthType = AuthType.USER,

    id: Long = 0L
) : BaseEntity(id) {

    fun updateProfile(target: Member) {
        this.nickname = target.nickname
        this.profileImage = target.profileImage
    }

    override fun toString(): String {
        return "Member(id='$id' oauthId=$oauthId, nickname='$nickname', profileImage='$profileImage', authType=$authType)"
    }
}
