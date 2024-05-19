package backend.itracker.crawl.macbook.service

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
            val maybeMacbook = macbookRepository.findByName(macbook.name)
            if (maybeMacbook.isEmpty) {
                macbookRepository.save(macbook)
                continue
            }
            maybeMacbook.get().addAllPrices(macbook.prices)
        }
    }

    @Transactional(readOnly = true)
    fun findAllWithRecentPrice(): List<Macbook> {
        TODO("쿼리 짜ㄱl")
        return emptyList()
    }
}
