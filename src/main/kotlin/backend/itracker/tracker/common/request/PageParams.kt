package backend.itracker.tracker.common.request

private const val DEFAULT_PAGE_SIZE = 10

private const val DEFAULT_PAGE_START_NUMBER = 1

class PageParams(
    private var page: Int = DEFAULT_PAGE_START_NUMBER,
    var limit: Int = DEFAULT_PAGE_SIZE
) {

    init {
        require(page >= DEFAULT_PAGE_START_NUMBER) { throw IllegalArgumentException("page는 ${DEFAULT_PAGE_START_NUMBER}이상 입력해주세요. page=$page") }
        require(limit >= DEFAULT_PAGE_START_NUMBER) { throw IllegalArgumentException("limit는 ${DEFAULT_PAGE_START_NUMBER}이상 입력해주세요. limit=$limit") }
    }

    val offset: Int
        get() = page - DEFAULT_PAGE_START_NUMBER
}
