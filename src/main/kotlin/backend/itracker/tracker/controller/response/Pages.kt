package backend.itracker.tracker.controller.response

data class Pages<T>(
    val data: List<T>
)

data class SinglePage<T>(
    val data: T
)
