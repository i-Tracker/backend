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

    fun updateAllMacbookCoupangLink(
        startId: Long,
        endId: Long,
    ): List<Deeplink> {
        val macbooks = macbookService.findByIdBetween(startId, endId)
        val coupangLinks = requestCoupangLinks(macbooks)

        return coupangLinks
    }

    private fun requestCoupangLinks(macbooks: List<Macbook>): MutableList<Deeplink> {
        val linkResults = mutableListOf<Deeplink>()
        val queue = ArrayDeque<String>()

        macbooks.map { it.productLink }.forEach {
            queue.add(it)
            if (queue.size == MAX_REQUEST_SIZE) {
                issueCoupangLinks(queue, linkResults)
            }
        }
        issueCoupangLinks(queue, linkResults)

        return linkResults
    }

    private fun issueCoupangLinks(
        queue: ArrayDeque<String>,
        linkResults: MutableList<Deeplink>
    ) {
        if (queue.isEmpty()) {
            return
        }

        val response = coupangApiClient.issueCoupangLinks(queue.toList())
        linkResults.addAll(response)
        queue.clear()
    }
}
