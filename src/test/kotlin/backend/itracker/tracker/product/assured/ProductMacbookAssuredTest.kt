package backend.itracker.tracker.product.assured

import backend.itracker.config.AssuredTestConfig
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.macbook.fixtures.MacbookFixture
import backend.itracker.crawl.macbook.fixtures.MacbookPriceFixture
import backend.itracker.tracker.common.PathParams
import backend.itracker.tracker.common.QueryParams
import backend.itracker.tracker.common.response.Pages
import backend.itracker.tracker.common.response.SingleData
import backend.itracker.tracker.product.response.filter.MacbookFilterResponse
import backend.itracker.tracker.product.response.product.macbook.MacbookDetailResponse
import backend.itracker.tracker.product.response.product.macbook.MacbookResponse
import io.restassured.common.mapper.TypeRef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ProductMacbookAssuredTest : AssuredTestConfig() {

    @Test
    fun `맥북 필터를 조회한다`() {
        // given
        saveMacbook(
            MacbookFixture.macbook(
                coupangId = 1,
                category = MacbookCategory.MACBOOK_AIR,
                size = 13,
                color = "스페이스 그레이",
                chip = "M1",
                storage = "256GB",
                memory = "8GB"
            )
        )
        saveMacbook(
            MacbookFixture.macbook(
                coupangId = 2,
                category = MacbookCategory.MACBOOK_AIR,
                size = 15,
                color = "실버",
                chip = "M1",
                storage = "512GB",
                memory = "16GB"
            )
        )

        // when
        val pathParams = PathParams(mapOf("category" to "macbook_air"))
        val response = get("/api/v1/products/{category}/filter", pathParams)
            .`as`(object : TypeRef<SingleData<MacbookFilterResponse>>() {})

        // then
        assertAll(
            { assertThat(response.data.size).containsExactly(13, 15) },
            { assertThat(response.data.color).containsExactly("스페이스 그레이", "실버") },
            { assertThat(response.data.processor).containsExactly("M1") },
            { assertThat(response.data.storage).containsExactly("256GB", "512GB") },
            { assertThat(response.data.memory).containsExactly("8GB", "16GB") }
        )
    }

    @Test
    fun `평균가와 비교해서 할인율이 높은 5개 상품을 조회한다`() {
        // given
        val fifth = saveMacbook(MacbookFixture.default().apply {
            addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000))
            addPrice(MacbookPriceFixture.macbookPrice(10, 10_000, 9_000))
        })
        val fourth = saveMacbook(MacbookFixture.default().apply {
            addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000))
            addPrice(MacbookPriceFixture.macbookPrice(20, 10_000, 8_000))
        })
        val first = saveMacbook(MacbookFixture.default().apply {
            addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000))
            addPrice(MacbookPriceFixture.macbookPrice(90, 10_000, 1_000))
        })
        saveMacbook(MacbookFixture.default().apply {
            addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000))
            addPrice(MacbookPriceFixture.macbookPrice(50, 20_000, 10_000))
        })
        val third = saveMacbook(MacbookFixture.default().apply {
            addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000))
            addPrice(MacbookPriceFixture.macbookPrice(30, 10_000, 7_000))
        })
        val second = saveMacbook(MacbookFixture.default().apply {
            addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000))
            addPrice(MacbookPriceFixture.macbookPrice(40, 10_000, 6_000))
        })

        val expected = listOf(
            MacbookResponse.from(first),
            MacbookResponse.from(second),
            MacbookResponse.from(third),
            MacbookResponse.from(fourth),
            MacbookResponse.from(fifth),
        )

        // when
        val pathParams = PathParams(mapOf("category" to "macbook_air"))
        val response = get("/api/v1/products/{category}", pathParams)
            .`as`(object : TypeRef<Pages<MacbookResponse>>() {})

        // then
        assertAll(
            { assertThat(response.data).hasSize(5) },
            { assertThat(response.data).isEqualTo(expected) }
        )
    }

    @Test
    fun `맥북을 상세 조회한다`() {
        // given
        val expected = saveMacbook(
            MacbookFixture.macbook(
                coupangId = 1,
                category = MacbookCategory.MACBOOK_AIR,
                size = 13,
                color = "스페이스 그레이",
                chip = "M1",
                storage = "256GB",
                memory = "8GB"
            ).apply { addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000)) }
        )
        // when
        val pathParams = PathParams(mapOf("category" to "macbook_air", "productId" to expected.id))
        val response = get("/api/v1/products/{category}/{productId}", pathParams)
            .`as`(object : TypeRef<MacbookDetailResponse>() {})

        // then
        assertThat(response).isEqualTo(MacbookDetailResponse.from(expected))
    }

    @ParameterizedTest
    @MethodSource("findFilteredMacbook")
    fun `필터링된 맥북을 조회한다`(queryParams: QueryParams) {
        // given
        val expected = saveMacbook(
            MacbookFixture.macbook(
                coupangId = 1,
                category = MacbookCategory.MACBOOK_AIR,
                size = 13,
                color = "스페이스 그레이",
                chip = "M1",
                storage = "256GB",
                memory = "8GB"
            ).apply { addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000)) }
        )
        saveMacbook(
            MacbookFixture.macbook(
                coupangId = 2,
                category = MacbookCategory.MACBOOK_AIR,
                size = 15,
                color = "실버",
                chip = "M2",
                storage = "512GB",
                memory = "16GB"
            ).apply { addPrice(MacbookPriceFixture.macbookPrice(0, 100_000, 100_000)) }
        )

        // when
        val pathParams = PathParams(mapOf("category" to "macbook_air"))
        val response = get("/api/v1/products/{category}/search", pathParams, queryParams)
            .`as`(object : TypeRef<Pages<MacbookResponse>>() {})

        // then
        assertAll(
            { assertThat(response.data).hasSize(1) },
            { assertThat(response.data).containsExactly(MacbookResponse.from(expected)) }
        )
    }

    companion object {

        @JvmStatic
        fun findFilteredMacbook() = Stream.of(
            Arguments.of(QueryParams(mapOf("size" to "13"))),
            Arguments.of(QueryParams(mapOf("processor" to "M1"))),
            Arguments.of(QueryParams(mapOf("color" to "스페이스 그레이"))),
            Arguments.of(QueryParams(mapOf("storage" to "256GB"))),
            Arguments.of(QueryParams(mapOf("memory" to "8GB")))
        )
    }
}
