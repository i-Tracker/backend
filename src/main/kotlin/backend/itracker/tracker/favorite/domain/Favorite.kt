package backend.itracker.tracker.favorite.domain

import backend.itracker.crawl.common.BaseEntity
import backend.itracker.tracker.member.domain.Member
import jakarta.persistence.*

@Entity
@Table(
    name = "favorite",
    uniqueConstraints = [
        UniqueConstraint(
            name = "favorite_product_unique",
            columnNames = ["member_id", "product_id", "product_category"]
        )
    ]
)
class Favorite(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @Embedded
    val product: FavoriteProduct,

    id: Long = 0
) : BaseEntity(id)
