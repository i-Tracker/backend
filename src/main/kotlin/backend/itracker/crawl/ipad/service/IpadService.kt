package backend.itracker.crawl.ipad.service

import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.ipad.domain.repository.IpadRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class IpadService(
    private val ipadRepository: IpadRepository
) {

    fun saveAll(ipads: List<Ipad>) {
        for (ipad in ipads) {
            val maybeIpad = ipadRepository.findByCoupangId(ipad.coupangId)
            if (maybeIpad.isEmpty) {
                ipadRepository.save(ipad)
                continue
            }
            maybeIpad.get().addAllPrices(ipad.prices)
        }
    }
}
