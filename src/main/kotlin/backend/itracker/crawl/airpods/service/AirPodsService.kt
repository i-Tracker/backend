package backend.itracker.crawl.airpods.service

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.airpods.domain.repository.AirPodsRepository
import backend.itracker.crawl.airpods.domain.repository.getByIdAllFetch
import backend.itracker.crawl.common.PartnersLinkInfo
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

    fun updateAllPartnersLink(linkInformation: List<PartnersLinkInfo>) {
        val airPodses = airPodsRepository.findAll()
        linkInformation.forEach { info ->
            airPodses.filter { airPods -> airPods.productLink == info.originalUrl }
                .map { it.changePartnersLink(info.partnersUrl) }
        }
    }

    @Transactional(readOnly = true)
    fun findAllFetch(): List<AirPods> {
        return airPodsRepository.findAllFetch()
    }

    @Transactional(readOnly = true)
    fun findByIdAllFetch(productId: Long): AirPods {
        return airPodsRepository.getByIdAllFetch(productId)
    }

    @Transactional(readOnly = true)
    fun findByIdBetween(startId: Long, endId: Long): List<AirPods> {
        return airPodsRepository.findByIdBetween(startId, endId)
    }

    @Transactional(readOnly = true)
    fun findAllInIds(ids: List<Long>): List<AirPods> {
        return airPodsRepository.findAllInIds(ids)
    }
}
