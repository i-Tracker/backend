package backend.itracker.crawl.service

import backend.itracker.crawl.domain.MacBook
import backend.itracker.crawl.response.DefaultProduct
import backend.itracker.crawl.service.common.PriceParser
import io.github.oshai.kotlinlogging.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*


private const val USER_AGENT =
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
private val logger = KotlinLogging.logger {}

@Component
class CrawlService(
    private val crawlMapper: CrawlMapper,
) {

    fun crawlMacBook(category: Category): List<MacBook> {
        val url = "https://pages.coupang.com/p/${category.categoryId}"

        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments("--headless")
        chromeOptions.addArguments(USER_AGENT)
        chromeOptions.addArguments("--disable-gpu")
        chromeOptions.addArguments("--no-sandbox")
        chromeOptions.addArguments("--disable-dev-shm-usage")
        chromeOptions.addArguments("--disable-notifications")
        chromeOptions.addArguments("--disable-extensions")
        val driver = ChromeDriver(chromeOptions)
        val products = HashMap<String, DefaultProduct>()
        val wait = WebDriverWait(driver, Duration.ofSeconds(5))
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
                        productId = element.findElement(By.tagName("a")).getAttribute("id")
                    } catch (e: Exception) {
                        continue
                    }

                    if (products.containsKey(productId)) {
                        continue
                    }
                    val productLink = element.findElement(By.tagName("a")).getAttribute("href")
                    val names = element.text.split(System.lineSeparator())
                    val thumbnailLink = element.findElement(By.tagName("img")).getAttribute("src").toString()

                    if (!names[0].contains("맥북") || names[0].contains("정품")) {
                        continue
                    }

                    products[productId] = DefaultProduct(
                        productId = productId.toLong(),
                        category = element.findElement(By.xpath("..")).findElement(By.xpath(".."))
                            .findElement(By.className("product-list-header__title")).text,
                        names = names,
                        priceInfo = PriceParser.getDefaultPrice(element),
                        productLink = productLink,
                        thumbnailLink = thumbnailLink
                    )
                }
            }
            logger.warn { "element: ${products.size}" }
            var cnt = 1
            products.values.forEach {
                logger.warn { "${cnt++}번째 element: ${it}" }
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
