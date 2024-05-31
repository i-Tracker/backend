package backend.itracker.tracker.controller.request

class PageParams(
    private var page: Int = 1,
    var limit: Int = 8
) {

    init {
        require(page >= 1) { throw IllegalArgumentException("page는 1이상 입력해주세요. page=$page") }
        require(limit >= 1) { throw IllegalArgumentException("limit는 1이상 입력해주세요. limit=$limit") }
    }

    val offset: Int
        get() = page - 1
}



