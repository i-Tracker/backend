package backend.itracker.crawl.service.util.helper

import backend.itracker.crawl.service.vo.DefaultPrice
import backend.itracker.schedule.infra.notification.solapi.logger
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.springframework.stereotype.Component
import java.math.BigDecimal

private const val DISCOUNT_PERCENTAGE = "discount-price__percentage"
private const val BASE_PRICE = "discount-price__base-price"
private const val CURRENT_PRICE = "current-price__price"
private const val OUT_OF_STOCK = "product-unit-oos"


@Component
class PriceParser(
    private val helper: WebElementHelper
) {

    fun getDefaultPrice(element: WebElement): DefaultPrice {
        var discountPercentage = 0
        var basePrice = BigDecimal.ZERO
        if (helper.hasClass(element, DISCOUNT_PERCENTAGE)) {
            val discountPercentageElement = element.findElement(By.className(DISCOUNT_PERCENTAGE)).text
            val basePriceElement = element.findElement(By.className(BASE_PRICE)).text
            if (discountPercentageElement.isBlank() || basePriceElement.isBlank()) {
                return DefaultPrice.none()
            }
            discountPercentage =
                discountPercentageElement.split(System.lineSeparator())[0]
                    .replace("%", "").toIntOrNull() ?: loggingElement("discountPercent", discountPercentageElement)
            basePrice = basePriceElement
                .replace(",", "")
                .removeSuffix("원").toBigDecimal()
        }

        val currentPriceElement = element.findElement(By.className(CURRENT_PRICE)).text
        if (currentPriceElement.isBlank()) {
            return DefaultPrice.none()
        }
        val currentPrice = currentPriceElement
            .replace(",", "")
            .removeSuffix("원").toBigDecimal()

        var isOutOfStock = "재고 존재"
        if (helper.hasClass(element, OUT_OF_STOCK)) {
            isOutOfStock = element.findElement(By.className(OUT_OF_STOCK)).text
        }

        return DefaultPrice(
            discountPercentage = discountPercentage,
            basePrice = basePrice,
            discountPrice = currentPrice,
            isOutOfStock = isOutOfStock
        )
    }

    private fun loggingElement(name: String, element: String?): Int {
        logger.error { "할인율 파싱 중 오류가 발생했습니다. $name: $element" }
        return 0
    }
}
