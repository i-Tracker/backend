package backend.itracker.crawl.watch.service

import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.repository.AppleWatchRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AppleWatchService(
    private val appleWatchRepository: AppleWatchRepository
) {

    fun saveAll(appleWatches: List<AppleWatch>) {
        for (appleWatch in appleWatches) {
            val maybeAppleWatch = appleWatchRepository.findByCoupangId(appleWatch.coupangId)
            if (maybeAppleWatch.isEmpty) {
                appleWatchRepository.save(appleWatch)
                continue
            }
            maybeAppleWatch.get().addAllPrices(appleWatch.prices)
        }
    }
}
