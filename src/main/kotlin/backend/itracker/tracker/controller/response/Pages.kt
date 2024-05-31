package backend.itracker.tracker.controller.response

import org.springframework.data.domain.Page

private const val DEFAULT_PAGE_OFFSET = 1

data class Pages<T>(
    val data: List<T>,
    val pageInfo: PageInfo = PageInfo()
) {

    companion object {
        fun <T> withPagination(pagesData: Page<T>) = Pages(
            data = pagesData.content,
            pageInfo = PageInfo.from(pagesData)
        )
    }

}

data class SinglePage<T>(
    val data: T
)

data class PageInfo(
    val currentPage: Int = 0,
    val lastPage: Int = 0,
    val elementSize: Int = 0
) {

    companion object {
        fun <T> from(pagesData: Page<T>) = PageInfo(
            currentPage = pagesData.number + DEFAULT_PAGE_OFFSET,
            lastPage = pagesData.totalPages,
            elementSize = pagesData.numberOfElements
        )
    }
}
