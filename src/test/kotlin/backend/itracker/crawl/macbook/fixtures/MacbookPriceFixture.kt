package backend.itracker.crawl.macbook.fixtures

import backend.itracker.crawl.macbook.domain.MacbookPrice
import java.math.BigDecimal

abstract class MacbookPriceFixture {

    companion object {
        fun macbookPrice(
            discountPercentage: Int,
            basePrice: Long,
            currentPrice: Long,
            isOutOfStock: Boolean
        ) = MacbookPrice(
            discountPercentage = discountPercentage,
            basePrice = BigDecimal.valueOf(basePrice),
            currentPrice = BigDecimal.valueOf(currentPrice),
            isOutOfStock = isOutOfStock
        )

        fun macbookPrice(
            discountPercentage: Int,
            basePrice: Long,
            currentPrice: Long
        ): MacbookPrice {
            val macbookPrice = macbookPrice(discountPercentage, basePrice, currentPrice, false)
            return macbookPrice
        }

        fun macbookPrice(
            discountPercentage: Int,
            basePrice: Long,
            currentPrice: Long,
            beforeDay: Long
        ): MacbookPrice {
            val macbookPrice = macbookPrice(discountPercentage, basePrice, currentPrice, false)
            macbookPrice.createdAt = macbookPrice.createdAt.minusDays(beforeDay)
            return macbookPrice
        }
    }
}
