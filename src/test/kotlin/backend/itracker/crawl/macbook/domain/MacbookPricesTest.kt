package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.macbook.fixtures.MacbookPriceFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MacbookPricesTest {

    private lateinit var macbookPrices: MacbookPrices

    @BeforeEach
    fun setUp() {
        macbookPrices = MacbookPrices(
            mutableListOf(
                MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000, 1),
                MacbookPriceFixture.macbookPrice(20, 2_000_000, 1_600_000, 2),
                MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000, 3),
                MacbookPriceFixture.macbookPrice(20, 2_000_000, 1_600_000, 4),
                MacbookPriceFixture.macbookPrice(10, 2_000_000, 1_800_000, 5)
            )
        )
    }

    @Test
    fun `현재 가격을 구한다`() {
        // given
        val expectedCurrentPrice: Long = 900_000
        macbookPrices.add(MacbookPriceFixture.macbookPrice(10, 1_000_000, expectedCurrentPrice))

        // when
        val actual = macbookPrices.findCurrentPrice()

        // then
        assertThat(actual).isEqualTo(expectedCurrentPrice.toBigDecimal())
    }

    @Test
    fun `할인 퍼센트를 구한다`() {
        // when
        val actual = macbookPrices.findTodayDiscountPercentage()

        // then
        assertThat(actual).isEqualTo(-29)
    }
}
