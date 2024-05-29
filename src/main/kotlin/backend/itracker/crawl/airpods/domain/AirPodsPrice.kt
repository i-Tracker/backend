package backend.itracker.crawl.airpods.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "airpods_price")
class AirPodsPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "airpods_id", nullable = false, foreignKey = ForeignKey(name = "fk_airpods_price_airpods_id_ref_airpods_id")
    )
    var airPods: AirPods? = null

    fun changeAirPods(airPods: AirPods) {
        this.airPods = airPods
    }

    override fun toString(): String {
        return "AirPodsPrice(discountPercentage=$discountPercentage, basePrice=$basePrice, currentPrice=$currentPrice, isOutOfStock=$isOutOfStock)"
    }
}
