package backend.itracker.crawl.mac.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "mac_price")
class MacPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "mac_id", nullable = false, foreignKey = ForeignKey(name = "fk_mac_price_mac_id_ref_mac_id")
    )
    var mac: Mac? = null

    fun changeMac(mac: Mac) {
        this.mac = mac
    }

    override fun toString(): String {
        return "MacPrice(discountPercentage=$discountPercentage, basePrice=$basePrice, currentPrice=$currentPrice, isOutOfStock=$isOutOfStock)"
    }
}
