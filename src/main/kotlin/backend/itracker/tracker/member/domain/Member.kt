package backend.itracker.tracker.member.domain

import backend.itracker.crawl.common.BaseEntity
import backend.itracker.tracker.favorite.domain.Favorite
import backend.itracker.tracker.oauth.OauthId
import backend.itracker.tracker.oauth.OauthServerType
import jakarta.persistence.*

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
    var phoneNumber: String?,

    @Column(columnDefinition = "text")
    var profileImage: String,

    @Enumerated(EnumType.STRING)
    val authType: AuthType = AuthType.USER,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val favorites: List<Favorite> = mutableListOf(),

    id: Long = 0L
) : BaseEntity(id) {

    fun updateProfile(target: Member) {
        this.nickname = target.nickname
        this.profileImage = target.profileImage
        this.phoneNumber = target.phoneNumber
    }

    fun isAnonymous() = this.id == 0L

    override fun toString(): String {
        return "Member(oauthId=$oauthId, nickname='$nickname', phoneNumber='$phoneNumber', profileImage='$profileImage', authType=$authType, favorites=$favorites)"
    }

    companion object {
        fun anonymous() = Member(
            oauthId = OauthId("anonymous", OauthServerType.KAKAO),
            nickname = "익명",
            phoneNumber = null,
            profileImage = "익명",
        )
    }
}
