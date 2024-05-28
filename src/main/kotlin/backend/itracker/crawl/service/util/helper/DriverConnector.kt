package backend.itracker.crawl.service.util.helper

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL
import java.time.Duration

private const val USER_AGENT =
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
private const val DEFAULT_WAIT_TIME = 5L

@Component
class DriverConnector {

    @Value("\${selenium.driver-url}")
    private lateinit var driverUrl: String

    fun getDriver(): RemoteWebDriver {
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.addArguments(USER_AGENT)
        firefoxOptions.addArguments("--headless")
        firefoxOptions.addArguments("--no-sandbox")
        firefoxOptions.setCapability("acceptInsecureCerts", true)

        return RemoteWebDriver(URL(driverUrl), firefoxOptions)
    }

    fun getWaiter(driver: RemoteWebDriver): FluentWait<out WebDriver> {
        return WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME))
    }
}
