package backend.itracker.tracker.member.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

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
