package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "macbook_price")
class MacbookPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    id: Long = 0L
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    var macbook: Macbook? = null

    fun changeMacbook(macbook: Macbook) {
        this.macbook = macbook
    }

    override fun toString(): String {
        return "MacbookPrice(discountPercentage=$discountPercentage, basePrice=$basePrice, discountPrice=$currentPrice)"
    }
}
