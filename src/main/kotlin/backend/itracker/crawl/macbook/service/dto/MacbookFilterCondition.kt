package backend.itracker.crawl.macbook.service.dto

import backend.itracker.tracker.service.response.filter.CommonFilterModel

data class MacbookFilterCondition(
    val size: Int?,
    val color: String?,
    val processor: String?,
    val storage: String?,
    val memory: String?
) : CommonFilterModel {

    constructor(filter: Map<String, String>) : this(
        size = filter["size"]?.toInt(),
        color = filter["color"],
        processor = filter["processor"],
        storage = filter["storage"],
        memory = filter["memory"])
}
