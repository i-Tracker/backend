package backend.itracker.crawl.iphone.service

import backend.itracker.crawl.iphone.domain.Iphone
import backend.itracker.crawl.iphone.domain.repository.IphoneRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class IphoneService(
    private val iphoneRepository: IphoneRepository
) {

    fun saveAll(iphones: List<Iphone>) {
        for (iphone in iphones) {
            val maybeIphone = iphoneRepository.findByCoupangId(iphone.coupangId)
            if (maybeIphone.isEmpty) {
                iphoneRepository.save(iphone)
                continue
            }
            maybeIphone.get().addAllPrices(iphone.prices)
        }
    }
}
