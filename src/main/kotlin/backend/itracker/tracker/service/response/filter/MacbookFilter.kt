package backend.itracker.tracker.service.response.filter

data class MacbookFilter(
    val size: List<Int>,
    val color: List<String>,
    val processor: List<String>,
    val storage: List<String>,
    val memory: List<String>
) : CommonFilterModel
