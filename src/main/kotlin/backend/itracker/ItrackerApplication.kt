package backend.itracker

import backend.itracker.crawl.service.Category
import backend.itracker.crawl.service.CrawlMapper
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.common.Crawler
import backend.itracker.crawl.service.common.PriceParser
import backend.itracker.crawl.service.common.WebElementHelper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class ItrackerApplication

fun main(args: Array<String>) {
	var i = 0
	for (macBook in CrawlService(CrawlMapper(), Crawler(priceParser = PriceParser(WebElementHelper()), helper = WebElementHelper())).crawlMacBook(Category.MACBOOK)) {
		logger.warn { "${i++}번째 $macBook" }
	}

	runApplication<ItrackerApplication>(*args)
}
