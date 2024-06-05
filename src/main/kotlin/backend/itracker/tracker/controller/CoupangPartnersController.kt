package backend.itracker.tracker.controller

import backend.itracker.crawl.common.CoupangLinkInfo
import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.service.service.CoupangPartnersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CoupangPartnersController(
    private val coupangPartnersService: CoupangPartnersService,
    private val macbookService: MacbookService
) {

    @PatchMapping("/api/v1/coupang/deeplink/{category}")
    fun updateCoupangLink(
        @PathVariable category: ProductCategory,
        @RequestParam start: Long,
        @RequestParam end: Long,
    ): ResponseEntity<Unit> {
        return when (category) {
            ProductCategory.MACBOOK_AIR, ProductCategory.MACBOOK_PRO -> {
                val deeplinks = coupangPartnersService.updateAllMacbookCoupangLink(start, end)
                macbookService.updateAllCoupangLink(deeplinks.map { CoupangLinkInfo(it.originalUrl, it.shortenUrl)})
                ResponseEntity.ok().build()
            }

            else -> {
                ResponseEntity.badRequest().build()
            }
        }
    }
}
