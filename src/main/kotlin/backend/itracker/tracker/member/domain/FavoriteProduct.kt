package backend.itracker.tracker.member.domain

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.common.ProductFilterCategory
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class FavoriteProduct(

    val productId: Long,

    @Enumerated(EnumType.STRING)
    val productCategory: ProductCategory,
) {

    companion object {
        fun of(productId: Long, productFilterCategory: ProductFilterCategory): FavoriteProduct {
            return FavoriteProduct(productId, ProductCategory.from(productFilterCategory))
        }
    }

    override fun toString(): String {
        return "FavoriteProduct(productId=$productId, productCategory=$productCategory)"
    }
}
