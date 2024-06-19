package backend.itracker.tracker.product.response.filter

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.tracker.product.common.DataSizeComparator

private val dataSizeComparator: DataSizeComparator = DataSizeComparator()

data class MacbookFilterResponse(
    val size: List<Int>,
    val color: List<String>,
    val processor: List<String>,
    val storage: List<String>,
    val memory: List<String>
) : CommonFilterModel {

    companion object {
        fun from(macbooks: List<Macbook>): MacbookFilterResponse {
            return MacbookFilterResponse(
                size = macbooks.map { it.size }.distinct().sorted(),
                color = macbooks.map { it.color }.distinct().sorted(),
                processor = macbooks.map { it.chip }.distinct().sorted(),
                storage = macbooks.map { it.storage }.distinct().sortedWith(dataSizeComparator),
                memory = macbooks.map { it.memory }.distinct().sortedWith(dataSizeComparator)
            )
        }
    }
}

