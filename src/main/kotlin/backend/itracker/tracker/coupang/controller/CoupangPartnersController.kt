package backend.itracker.tracker.coupang.controller

import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.common.PartnersLinkInfo
import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.coupang.service.CoupangPartnersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CoupangPartnersController(
    private val coupangPartnersService: CoupangPartnersService,
    private val macbookService: MacbookService,
    private val airPodsService: AirPodsService
) {

    @PatchMapping("/api/v1/coupang/deeplink/{category}")
    fun updatePartnersLink(
        @PathVariable category: ProductCategory,
        @RequestParam start: Long,
        @RequestParam end: Long,
    ): ResponseEntity<Unit> {
        return when (category) {
            ProductCategory.MACBOOK_AIR, ProductCategory.MACBOOK_PRO -> {
                val partnersLink = coupangPartnersService.updateAllMacbookPartnersLink(start, end)
                macbookService.updateAllPartnersLink(partnersLink.map { PartnersLinkInfo(it.originalUrl, it.shortenUrl)})
                ResponseEntity.ok().build()
            }

            ProductCategory.AIRPODS -> {
                val partnersLink = coupangPartnersService.updateAllAirPodsPartnersLink(start, end)
                airPodsService.updateAllPartnersLink(partnersLink.map { PartnersLinkInfo(it.originalUrl, it.shortenUrl)})
                ResponseEntity.ok().build()
            }

            else -> {
                ResponseEntity.badRequest().build()
            }
        }
    }
}
