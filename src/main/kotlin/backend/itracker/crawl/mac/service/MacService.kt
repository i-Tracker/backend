package backend.itracker.crawl.mac.service

import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.mac.domain.repository.MacRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MacService(
    private val macRepository: MacRepository
) {

    fun saveAll(macs: List<Mac>) {
        for (mac in macs) {
            val maybeMac = macRepository.findByCoupangId(mac.coupangId)
            if (maybeMac.isEmpty) {
                macRepository.save(mac)
                continue
            }
            maybeMac.get().addAllPrices(mac.prices)
        }
    }
}
