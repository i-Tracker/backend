package backend.itracker.crawl.ipad.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "ipad_price")
class IpadPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "ipad_id", nullable = false, foreignKey = ForeignKey(name = "fk_ipad_price_ipad_id_ref_ipad_id")
    )
    var ipad: Ipad? = null

    fun changeIpad(ipad: Ipad) {
        this.ipad = ipad
    }

    override fun toString(): String {
        return "IpadPrice(isOutOfStock=$isOutOfStock, currentPrice=$currentPrice, basePrice=$basePrice, discountPercentage=$discountPercentage)"
    }
}
