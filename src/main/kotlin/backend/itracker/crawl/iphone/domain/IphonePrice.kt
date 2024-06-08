package backend.itracker.crawl.iphone.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "iphone_price")
class IphonePrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "iphone_id", nullable = false, foreignKey = ForeignKey(name = "fk_iphone_price_iphone_id_ref_iphone_id")
    )
    var iphone: Iphone? = null

    fun changeIphone(iphone: Iphone) {
        this.iphone = iphone
    }

    override fun toString(): String {
        return "IphonePrice(discountPercentage=$discountPercentage, basePrice=$basePrice, currentPrice=$currentPrice, isOutOfStock=$isOutOfStock)"
    }
}
