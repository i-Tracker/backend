package backend.itracker.tracker.service.service

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.service.response.Deeplink
import org.springframework.stereotype.Service

private const val MAX_REQUEST_SIZE = 20

@Service
class CoupangPartnersService(
    private val coupangApiClient: CoupangApiClient,
    private val macbookService: MacbookService
) {

    fun updateAllMacbookPartnersLink(
        startId: Long,
        endId: Long,
    ): List<Deeplink> {
        val macbooks = macbookService.findByIdBetween(startId, endId)
        val deepLinks = requestDeepLinks(macbooks)

        return deepLinks
    }

    private fun requestDeepLinks(macbooks: List<Macbook>): MutableList<Deeplink> {
        val linkResults = mutableListOf<Deeplink>()
        val queue = ArrayDeque<String>()

        macbooks.map { it.productLink }.forEach {
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
