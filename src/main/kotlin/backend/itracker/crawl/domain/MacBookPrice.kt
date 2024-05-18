package backend.itracker.crawl.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import java.math.BigDecimal

@Embeddable
@Entity
class MacBookPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
) : BaseEntity() {

    override fun toString(): String {
        return "MacBookPrice(discountPercentage=$discountPercentage, basePrice=$basePrice, discountPrice=$discountPrice)"
    }
}
