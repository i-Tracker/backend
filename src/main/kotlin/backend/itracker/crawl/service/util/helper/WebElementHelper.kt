package backend.itracker.crawl.service.util.helper

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.springframework.stereotype.Component

@Component
class WebElementHelper {

    fun hasClass(element: WebElement, className: String): Boolean {
        return element.findElements(By.className(className)).isNotEmpty()
    }

    fun findClassName(element: WebElement, className: String): String {
        return element.findElement(By.className(className)).text
    }

    fun findByTagAndAttribute(element: WebElement, tag: String, attribute: String): String {
        return element.findElement(By.tagName(tag)).getAttribute(attribute)
    }

    fun findGrandParentElement(element: WebElement): WebElement {
        return element.findElement(By.xpath(".."))
            .findElement(By.xpath(".."))
    }
}
