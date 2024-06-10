package backend.itracker.crawl.airpods.service

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.airpods.domain.repository.AirPodsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AirPodsService(
    private val airPodsRepository: AirPodsRepository
) {
    
    fun saveAll(airPodses: List<AirPods>) {
        for (airPods in airPodses) {
            val maybeAirPods = airPodsRepository.findByCoupangId(airPods.coupangId)
            if (maybeAirPods.isEmpty) {
                airPodsRepository.save(airPods)
                continue
            }
            maybeAirPods.get().addAllPrices(airPods.prices)
        }
    }

    @Transactional(readOnly = true)
    fun findAllFetch(): List<AirPods> {
        return airPodsRepository.findAllFetch()
    }
}
