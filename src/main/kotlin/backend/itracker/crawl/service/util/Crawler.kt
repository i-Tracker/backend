package backend.itracker.crawl.service.util

import backend.itracker.crawl.exception.CrawlException
import backend.itracker.crawl.service.util.helper.DriverConnector
import backend.itracker.crawl.service.util.helper.PriceParser
import backend.itracker.crawl.service.util.helper.WebElementHelper
import backend.itracker.crawl.service.vo.DefaultProduct
import io.github.oshai.kotlinlogging.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.springframework.stereotype.Component

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
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product-list-contents__product-unit")))
                val newHeight = driver.executeScript("return window.pageYOffset")
                if (lastHeight == newHeight) {
                    break
                }

                for (element in driver.findElements(By.className("product-list-contents__product-unit"))) {
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
                            "product-list-header__title"
                        ),
                        name = element.text.split(System.lineSeparator())[0],
                        price = priceParser.getDefaultPrice(element),
                        productLink = helper.findByTagAndAttribute(element, "a", "href"),
                        thumbnailLink = helper.findByTagAndAttribute(element, "img", "src")
                    )
                }
            }
        } catch (e: Exception) {
            logger.error(e) { "크롤링 중에 오류가 발생했습니다. url: $targetUrl" }
            throw CrawlException("크롤링 중에 오류가 발생했습니다. url: $targetUrl")
        } finally {
            driver.quit()
        }

        return products
    }

    fun crawlWithClick(targetUrl: String): Map<String, DefaultProduct> {
        val driver = driverConnector.getDriver()
        val wait = driverConnector.getWaiter(driver)

        val products = HashMap<String, DefaultProduct>()
        try {
            driver.get(targetUrl)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tabs")))

            val tabs = driver.findElements(By.className("tab"))
            for (tab in tabs) {
                try {
                    tab.click()
                } catch (e: ElementClickInterceptedException) {
                    logger.error(e) { "ElementClickInterceptedException 발생" }
                    continue
                }

                val subcategory = tab.text
                val maxClicks =
                    driver.findElement(By.className("carousel-header__nav")).text.toCharArray().last().toString()
                        .toInt()

                val buttons = driver.findElements(By.ByClassName("carousel-contents__nav--next"))
                val targetButton = buttons.firstOrNull {
                    helper.findGrandParentElement(it)
                        .findElement(By.className("carousel-header__title")).text == subcategory
                } ?: return products

                var clickCount = 0
                while (clickCount++ < maxClicks) {
                    targetButton.click()
                    for (element in driver.findElements(By.className("carousel-contents-grid__product-unit"))) {
                        val productId = helper.findByTagAndAttribute(element, "a", "id")
                        val productLink = helper.findByTagAndAttribute(element, "a", "href")
                        val thumbnailLink = helper.findByTagAndAttribute(element, "img", "src")
                        val name = element.findElement(By.className("product-unit-info__title")).text
                        val price = priceParser.getDefaultPrice(element)
                        val subCategory = helper.findGrandGrandParentElement(element)
                            .findElement(By.className("carousel-header__title")).text

                        products[productId] = DefaultProduct(
                            productId = productId.toLong(),
                            subCategory = subCategory,
                            name = name,
                            price = price,
                            productLink = productLink,
                            thumbnailLink = thumbnailLink
                        )
                    }
                }
            }
        } catch (e: Exception) {
            logger.error(e) { "크롤링 중에 오류가 발생했습니다. url: $targetUrl" }
            throw CrawlException("크롤링 중에 오류가 발생했습니다. url: $targetUrl")
        } finally {
            driver.quit()
        }

        return products
    }
}
