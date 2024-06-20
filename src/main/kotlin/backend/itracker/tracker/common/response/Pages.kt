package backend.itracker.tracker.common.response

import org.springframework.data.domain.Page

private const val DEFAULT_PAGE_OFFSET = 1

data class Pages<T>(
    val data: List<T>,
    val pageInfo: PageInfo = PageInfo()
) {

    companion object {
        fun <T> withPagination(data: List<T>) = Pages(
            data = data,
            pageInfo = PageInfo(currentPage = 1, lastPage = 1, elementSize = data.size)
        )
        fun <T> withPagination(pagesData: Page<T>) = Pages(
            data = pagesData.content,
            pageInfo = PageInfo.from(pagesData)
        )
    }

}

data class SingleData<T>(
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
