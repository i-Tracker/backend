package backend.itracker.tracker.favorite.domain

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FavoriteProduct

        if (productId != other.productId) return false
        if (productCategory != other.productCategory) return false

        return true
    }

    override fun hashCode(): Int {
        var result = productId.hashCode()
        result = 31 * result + productCategory.hashCode()
        return result
    }
}
