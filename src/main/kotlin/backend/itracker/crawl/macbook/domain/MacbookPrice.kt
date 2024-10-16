package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "macbook_price")
class MacbookPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "macbook_id", nullable = false, foreignKey = ForeignKey(name = "fk_macbook_price_macbook_id_ref_macbook_id")
    )
    var macbook: Macbook? = null

    fun changeMacbook(macbook: Macbook) {
        this.macbook = macbook
    }

    fun isSmallerThan(targetPrice: MacbookPrice): Boolean {
        return this.currentPrice < targetPrice.currentPrice
    }

    override fun toString(): String {
        return "MacbookPrice(discountPercentage=$discountPercentage, basePrice=$basePrice, discountPrice=$currentPrice)"
    }
}
