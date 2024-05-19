package backend.itracker.crawl.service.util.helper

import backend.itracker.crawl.service.vo.DefaultPrice
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.springframework.stereotype.Component
import java.math.BigDecimal

private const val DISCOUNT_PERCENTAGE = "discount-price__percentage"
private const val BASE_PRICE = "discount-price__base-price"

private const val CURRENT_PRICE = "current-price__price"


@Component
class PriceParser(
    private val helper: WebElementHelper
) {

    fun getDefaultPrice(element: WebElement): DefaultPrice {
        var discountPercentage = 0
        var basePrice = BigDecimal.ZERO
        if (helper.hasClass(element, DISCOUNT_PERCENTAGE)) {
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

        return DefaultPrice(
            discountPercentage = discountPercentage,
            basePrice = basePrice,
            discountPrice = currentPrice
        )
    }
}
