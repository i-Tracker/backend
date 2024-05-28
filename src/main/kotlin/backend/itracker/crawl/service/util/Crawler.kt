package backend.itracker.crawl.service.util

import backend.itracker.crawl.service.util.helper.DriverConnector
import backend.itracker.crawl.service.util.helper.PriceParser
import backend.itracker.crawl.service.util.helper.WebElementHelper
import backend.itracker.crawl.service.vo.DefaultProduct
import io.github.oshai.kotlinlogging.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.springframework.stereotype.Component

private const val PRODUCT_LIST_HEADER_TITLE = "product-list-header__title"
private const val CONTENTS_CLASS = "product-list-contents__product-unit"

private val logger = KotlinLogging.logger {}

@Component
class Crawler(
    private val helper: WebElementHelper,
    private val priceParser: PriceParser,
    private val driverConnector: DriverConnector
) {

    fun crawl(targetUrl: String): Map<String, DefaultProduct> {
        val driver = driverConnector.getDriver()
        val wait = driverConnector.getWaiter(driver)

        val products = HashMap<String, DefaultProduct>()
        try {
            driver.get(targetUrl)
            while (true) {
                val lastHeight = driver.executeScript("return window.pageYOffset")
                driver.executeScript("window.scrollTo(0, window.pageYOffset + 400)")
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className(CONTENTS_CLASS)))
                val newHeight = driver.executeScript("return window.pageYOffset")
                if (lastHeight == newHeight) {
                    break
                }

                for (element in driver.findElements(By.className(CONTENTS_CLASS))) {
                    var productId = ""
                    try {
                        productId = helper.findByTagAndAttribute(element, "a", "id")
                    } catch (exception: NoSuchElementException) {
                        continue
                    } catch (exception: StaleElementReferenceException) {
                        continue
                    } catch (exception: Exception) {
                        logger.error(exception) { "크롤링 중에 오류가 발생했습니다. url: $targetUrl" }
                    }

                    if (productId.isBlank() || products.containsKey(productId)) {
                        continue
                    }


                    products[productId] = DefaultProduct(
                        productId = productId.toLong(),
                        subCategory = helper.findClassName(
                            helper.findGrandParentElement(element),
                            PRODUCT_LIST_HEADER_TITLE
                        ),
                        names = element.text.split(System.lineSeparator()),
                        priceInfo = priceParser.getDefaultPrice(element),
                        productLink = helper.findByTagAndAttribute(element, "a", "href"),
                        thumbnailLink = helper.findByTagAndAttribute(element, "img", "src")
                    )
                }
            }
            return products
        } finally {
            driver.quit()
        }

        throw IllegalStateException("크롤링 중에 오류가 발생했습니다. url: $targetUrl")
    }
}
