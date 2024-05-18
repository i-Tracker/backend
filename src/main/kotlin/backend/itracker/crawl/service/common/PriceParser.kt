package backend.itracker.crawl.service.common

import backend.itracker.crawl.response.DefaultPrice
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.math.BigDecimal

private const val DISCOUNT_PERCENTAGE = "discount-price__percentage"
private const val BASE_PRICE = "discount-price__base-price"

private const val CURRENT_PRICE = "current-price__price"

private const val OUT_OF_STOCK = "product-unit-oos"

class PriceParser {

    companion object {
        fun getDefaultPrice(element: WebElement): DefaultPrice {
            var discountPercentage = 0
            var basePrice = BigDecimal.ZERO
            if (hasClass(element, DISCOUNT_PERCENTAGE)) {
                discountPercentage =
                    element.findElement(By.className(DISCOUNT_PERCENTAGE)).text.split(System.lineSeparator())[0]
                        .replace("%", "").toInt()
                basePrice = element.findElement(By.className(BASE_PRICE)).text
                    .replace(",", "")
                    .removeSuffix("원").toBigDecimal()
            }

            val currentPrice = element.findElement(By.className(CURRENT_PRICE)).text
                .replace(",", "")
                .removeSuffix("원").toBigDecimal()

            var isOutOfStock = "재고 존재"
            if (hasClass(element, OUT_OF_STOCK)) {
                isOutOfStock = element.findElement(By.className(OUT_OF_STOCK)).text
            }

            return DefaultPrice(
                discountPercentage = discountPercentage,
                basePrice = basePrice,
                discountPrice = currentPrice,
                isOutOfStock = isOutOfStock
            )
        }

        private fun hasClass(element: WebElement, className: String): Boolean {
            return element.findElements(By.className(className)).isNotEmpty()
        }
    }
}
