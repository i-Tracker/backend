package backend.itracker.crawl.domain

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "macbook_price")
class MacbookPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
) : BaseEntity() {

    override fun toString(): String {
        return "MacBookPrice(discountPercentage=$discountPercentage, basePrice=$basePrice, discountPrice=$discountPrice)"
    }
}
