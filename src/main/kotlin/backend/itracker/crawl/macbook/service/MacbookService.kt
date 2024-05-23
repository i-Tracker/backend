package backend.itracker.crawl.macbook.service

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MacbookService (
    private val macbookRepository: MacbookRepository
){

    fun saveAll(macbooks: List<Macbook>) {
        for (macbook in macbooks) {
            val maybeMacbook = macbookRepository.findByCoupangId(macbook.coupangId)
            if (maybeMacbook.isEmpty) {
                macbookRepository.save(macbook)
                continue
            }
            maybeMacbook.get().addAllPrices(macbook.prices)
        }
    }

    @Transactional(readOnly = true)
    fun findAllWithRecentPricesByProductGategory(crawlTargetCategory: ProductCategory): List<Macbook> {
        val macBooks = macbookRepository.findAllFetchByProductCategory(crawlTargetCategory)
        macBooks.map { it.keepOnlyRecentPrice() }
        return macBooks
    }

    @Transactional(readOnly = true)
    fun findAllFetchByProductCategory(
        productCategory: ProductCategory,
    ): List<Macbook> {
        return macbookRepository.findAllFetchByProductCategory(productCategory)
    }
}
