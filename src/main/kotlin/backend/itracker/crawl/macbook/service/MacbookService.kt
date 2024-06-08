package backend.itracker.crawl.macbook.service

import backend.itracker.crawl.common.CoupangLinkInfo
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.macbook.domain.repository.MacbookRepository
import backend.itracker.crawl.macbook.domain.repository.findByIdAllFetch
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
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

    fun updateAllCoupangLink(coupangLinks: List<CoupangLinkInfo>) {
        val macbooks = macbookRepository.findAll()
        coupangLinks.forEach { link ->
            val coupangLink = link.partnersUrl

            macbooks.filter { macbook -> macbook.productLink == link.originalUrl }
                .map { it.changeCoupangLink(coupangLink) }
        }
    }

    @Transactional(readOnly = true)
    fun findAllFetchByCategory(
        category: MacbookCategory,
    ): List<Macbook> {
        return macbookRepository.findAllFetchByProductCategory(category)
    }

    @Transactional(readOnly = true)
    fun findAllByProductCategoryAndFilter(
        category: MacbookCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook> {
        return macbookRepository.findAllByFilterCondition(category, filterCondition)
    }

    @Transactional(readOnly = true)
    fun findAllProductsByFilter(
        category: MacbookCategory,
        macbookFilterCondition: MacbookFilterCondition,
    ): List<Macbook> {
        return macbookRepository.findAllFetchBySearchCondition(category, macbookFilterCondition)
    }

    @Transactional(readOnly = true)
    fun findMacbookById(macbookId: Long): Macbook {
        return macbookRepository.findByIdAllFetch(macbookId)
    }

    @Transactional(readOnly = true)
    fun findByIdBetween(startId: Long, endId: Long): List<Macbook> {
        return macbookRepository.findByIdBetween(startId, endId)
    }
}
