package backend.itracker.tracker.member.domain

import backend.itracker.crawl.common.ProductFilterCategory
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class FavoriteProduct(

    val productId: Long,

    @Enumerated(EnumType.STRING)
    val productFilterCategory: ProductFilterCategory,
) {

    override fun toString(): String {
        return "FavoriteProduct(productId=$productId, productCategory=$productFilterCategory)"
    }
}
