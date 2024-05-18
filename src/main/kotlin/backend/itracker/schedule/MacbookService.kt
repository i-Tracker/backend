package backend.itracker.schedule

import backend.itracker.crawl.domain.Macbook
import backend.itracker.crawl.domain.MacbookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MacbookService (
    private val macbookRepository: MacbookRepository
){

    @Transactional
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
}
