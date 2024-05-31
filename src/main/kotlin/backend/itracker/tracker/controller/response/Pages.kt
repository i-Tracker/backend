package backend.itracker.tracker.controller.response

data class Pages<T>(
    val data: List<T>,
    val pageInfo: PageInfo = PageInfo()
)

data class SinglePage<T>(
    val data: T
)

data class PageInfo(
    val currentPage: Int = 0,
    val lastPage: Int = 0,
    val elementSize: Int = 0
)


