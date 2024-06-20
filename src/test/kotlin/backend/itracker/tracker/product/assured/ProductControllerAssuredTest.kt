package backend.itracker.tracker.product.assured

import backend.itracker.config.AssuredTestConfig
import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.product.response.CategoryResponses
import io.restassured.common.mapper.TypeRef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductControllerAssuredTest : AssuredTestConfig() {

    @Test
    fun `모든 카테고리를 조회한다`() {
        // when
        val response = get("/api/v1/category")
            .`as`(object : TypeRef<CategoryResponses>() {})

        // then
        val expected = ProductCategory.entries.map { it.name.lowercase() }.toList()
        assertThat(response.categories).isEqualTo(expected)
    }
}
