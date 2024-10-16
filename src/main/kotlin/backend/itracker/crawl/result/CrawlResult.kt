package backend.itracker.crawl.result

import backend.itracker.crawl.common.BaseEntity
import backend.itracker.crawl.service.CrawlTargetCategory
import jakarta.persistence.Entity

@Entity
class CrawlResult(

    var airpods: Int = 0,
    var ipad: Int = 0,
    var iphone: Int = 0,
    var mac: Int = 0,
    var macbook: Int = 0,
    var appleWatch: Int = 0,
    id: Long = 0L,
) : BaseEntity(id) {

    override fun toString(): String {
        return "CrawlResult(airpods=$airpods, ipad=$ipad, iphone=$iphone, mac=$mac, macbook=$macbook, appleWatch=$appleWatch)"
    }

    fun update(result: CrawlResult) {
        this.airpods += result.airpods
        this.ipad += result.ipad
        this.iphone += result.iphone
        this.mac += result.mac
        this.macbook += result.macbook
        this.appleWatch += result.appleWatch
    }

    fun alreadyCrawlAirpods(): Boolean {
        return airpods > 0
    }

    fun alreadyCrawlIpad(): Boolean {
        return ipad > 0
    }

    fun alreadyCrawlIphone(): Boolean {
        return iphone > 0
    }

    fun alreadyCrawlMac(): Boolean {
        return mac > 0
    }

    fun alreadyCrawlMacbook(): Boolean {
        return macbook > 0
    }

    fun alreadyCrawlAppleWatch(): Boolean {
        return appleWatch > 0
    }

    fun hasError(): Boolean {
        return !(alreadyCrawlMacbook() && alreadyCrawlIpad() && alreadyCrawlAppleWatch() && alreadyCrawlMac() && alreadyCrawlAirpods() && alreadyCrawlIphone())
    }

    companion object {
        fun from(crawlCategory: CrawlTargetCategory): CrawlResult =
            when (crawlCategory) {
                CrawlTargetCategory.MACBOOK -> CrawlResult(macbook = 1)
                CrawlTargetCategory.IPAD -> CrawlResult(ipad = 1)
                CrawlTargetCategory.APPLE_WATCH -> CrawlResult(appleWatch = 1)
                CrawlTargetCategory.MAC -> CrawlResult(mac = 1)
                CrawlTargetCategory.AIRPODS -> CrawlResult(airpods = 1)
                CrawlTargetCategory.IPHONE -> CrawlResult(iphone = 1)
            }
    }
}
