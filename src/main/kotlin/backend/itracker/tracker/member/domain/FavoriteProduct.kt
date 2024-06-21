package backend.itracker.tracker.member.domain

import backend.itracker.crawl.common.ProductCategory
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class FavoriteProduct(

    val productId: Long,

    @Enumerated(EnumType.STRING)
    val productCategory: ProductCategory,
) {

    override fun toString(): String {
        return "FavoriteProduct(productId=$productId, productCategory=$productCategory)"
    }
}
