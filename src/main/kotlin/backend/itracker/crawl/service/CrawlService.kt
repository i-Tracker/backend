package backend.itracker.crawl.service

import backend.itracker.crawl.domain.MacBook
import backend.itracker.crawl.response.DefaultProduct
import backend.itracker.crawl.service.common.DriverHelper
import backend.itracker.crawl.service.common.PriceParser
import backend.itracker.crawl.service.common.WebElementHelper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.springframework.stereotype.Component
import java.util.*


private const val PRODUCT_LIST_HEADER_TITLE = "product-list-header__title"
private val logger = KotlinLogging.logger {}

@Component
class CrawlService(
    private val crawlMapper: CrawlMapper,
    private val priceParser: PriceParser,
    private val helper: WebElementHelper,
    private val driverHelper: DriverHelper = DriverHelper()
) {

    fun crawlMacBook(category: Category): List<MacBook> {
        val url = "https://pages.coupang.com/p/${category.categoryId}"
        val driver = driverHelper.getDriver()
        val wait = driverHelper.getWaiter(driver)

        val products = HashMap<String, DefaultProduct>()
        try {
            driver.get(url)
            while (true) {
                val lastHeight = driver.executeScript("return window.pageYOffset")

                driver.executeScript("window.scrollTo(0, window.pageYOffset + 400)")
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product-list-contents__product-unit")))
                val newHeight = driver.executeScript("return window.pageYOffset")
                if (lastHeight == newHeight) {
                    break
                }
                for (element in driver.findElements(By.className("product-list-contents__product-unit"))) {
                    var productId = ""
                    try {
                        productId = helper.findByTagAndAttribute(element, "a", "id")
                    } catch (e: Exception) {
                        continue
                    }

                    if (products.containsKey(productId)) {
                        continue
                    }
                    val names = element.text.split(System.lineSeparator())
                    if (!names[0].contains("맥북") || names[0].contains("정품")) {
                        continue
                    }

                    val productLink = helper.findByTagAndAttribute(element, "a", "href")
                    val thumbnailLink = helper.findByTagAndAttribute(element, "img", "src")

                    products[productId] = DefaultProduct(
                        productId = productId.toLong(),
                        category = helper.findClassName(
                            helper.findGrandParentElement(element),
                            PRODUCT_LIST_HEADER_TITLE
                        ),
                        names = names,
                        priceInfo = priceParser.getDefaultPrice(element),
                        productLink = productLink,
                        thumbnailLink = thumbnailLink
                    )
                }
            }
            return crawlMapper.toMacBook(products)

        } catch (e: Exception) {
            logger.error(e) { "cause: $e" }
        } finally {
            driver.quit()
        }

        return Collections.emptyList()
    }

}
