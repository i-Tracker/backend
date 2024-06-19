package backend.itracker.tracker.coupang.service

import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.coupang.client.CoupangApiClient
import backend.itracker.tracker.coupang.response.Deeplink
import org.springframework.stereotype.Service

private const val MAX_REQUEST_SIZE = 20

@Service
class CoupangPartnersService(
    private val coupangApiClient: CoupangApiClient,
    private val macbookService: MacbookService,
    private val airPodsService: AirPodsService,
) {

    fun updateAllMacbookPartnersLink(
        startId: Long,
        endId: Long,
    ): List<Deeplink> {
        val macbookProductLinks = macbookService.findByIdBetween(startId, endId)
            .map { it.productLink }

        return requestDeepLinks(macbookProductLinks)
    }

    fun updateAllAirPodsPartnersLink(
        startId: Long,
        end: Long
    ): List<Deeplink> {
        val airPodsProductLinks = airPodsService.findByIdBetween(startId, end)
            .map { it.productLink }
        return requestDeepLinks(airPodsProductLinks)
    }

    private fun requestDeepLinks(productLinks: List<String>): MutableList<Deeplink> {
        val linkResults = mutableListOf<Deeplink>()
        val queue = ArrayDeque<String>()

        productLinks.forEach {
            queue.add(it)
            if (queue.size == MAX_REQUEST_SIZE) {
                issueDeepLinks(queue, linkResults)
            }
        }
        issueDeepLinks(queue, linkResults)

        return linkResults
    }

    private fun issueDeepLinks(
        queue: ArrayDeque<String>,
        linkResults: MutableList<Deeplink>
    ) {
        if (queue.isEmpty()) {
            return
        }

        val response = coupangApiClient.issueDeepLinks(queue.toList())
        linkResults.addAll(response)
        queue.clear()
    }
}
