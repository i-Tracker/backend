package backend.itracker.crawl.macbook.service

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.repository.MacbookRepository
import backend.itracker.crawl.macbook.domain.repository.findByIdAllFetch
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import backend.itracker.tracker.service.response.Deeplink
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MacbookService(
    private val macbookRepository: MacbookRepository
) {

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
    fun findAllFetchByProductCategory(
        productCategory: ProductCategory,
    ): List<Macbook> {
        return macbookRepository.findAllFetchByProductCategory(productCategory)
    }

    @Transactional(readOnly = true)
    fun findAllByProductCategoryAndFilter(
        productCategory: ProductCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook> {
        return macbookRepository.findAllByFilterCondition(productCategory, filterCondition)
    }

    @Transactional(readOnly = true)
    fun findAllProductsByFilter(
        category: ProductCategory,
        macbookFilterCondition: MacbookFilterCondition,
        pageable: Pageable
    ): Page<Macbook> {
        val pageMacbooks =
            macbookRepository.findAllProductsByFilter(category, macbookFilterCondition, pageable)
        pageMacbooks.forEach { macbookRepository.findAllPricesByMacbookId(it.id) }

        return pageMacbooks
    }

    @Transactional(readOnly = true)
    fun findMacbookById(macbookId: Long): Macbook {
        return macbookRepository.findByIdAllFetch(macbookId)
    }

    @Transactional(readOnly = true)
    fun findByIdBetween(startId: Long, endId: Long): List<Macbook> {
        return macbookRepository.findByIdBetween(startId, endId)
    }

    fun updateAllCoupangLink(deeplinks: List<Deeplink>) {
        val macbooks = macbookRepository.findAll()
        deeplinks.forEach {
            val productLink = it.originalUrl
            val coupangLink = it.shortenUrl

            macbooks.filter { it.productLink == productLink }
                .map { it.changeCoupangLink(coupangLink) }
        }
    }
}
