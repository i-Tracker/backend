package backend.itracker.crawl.service.util.helper

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

private const val USER_AGENT =
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
private const val DEFAULT_WAIT_TIME = 5L

class DriverConnector {

    fun getDriver(): RemoteWebDriver {
        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments(USER_AGENT)
        chromeOptions.addArguments("--headless")
        chromeOptions.addArguments("--disable-gpu")
        chromeOptions.addArguments("--no-sandbox")
        chromeOptions.addArguments("--disable-dev-shm-usage")
        chromeOptions.addArguments("--disable-notifications")
        chromeOptions.addArguments("--disable-extensions")

        return ChromeDriver(chromeOptions)
    }

    fun getWaiter(driver: RemoteWebDriver): FluentWait<out WebDriver> {
        return WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME))
    }
}
