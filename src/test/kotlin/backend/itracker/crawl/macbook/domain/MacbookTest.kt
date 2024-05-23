package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.macbook.fixtures.MacbookFixture
import backend.itracker.crawl.macbook.fixtures.MacbookPriceFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MacbookTest {

    private lateinit var macbook: Macbook

    @BeforeEach
    fun setUp() {
        macbook = MacbookFixture.createDefaultMacbookAir()
    }

    @Test
    fun `맥북에 가격에 추가한다`() {
        // given
        val targetPrice = MacbookPriceFixture.macbookPrice(2, 2_400_000, 2_351_000)

        // when
        macbook.addPrice(targetPrice)

        // then
        assertThat(macbook.prices.macbookPrices).containsOnly(targetPrice)
    }

    @Test
    fun `맥북에 가격들을 추가한다`() {
        // given
        val targetPrices = MacbookPrices(
            mutableListOf(
                MacbookPriceFixture.macbookPrice(2, 2_400_000, 2_351_000),
                MacbookPriceFixture.macbookPrice(10, 2_000_000, 1_800_000),
                MacbookPriceFixture.macbookPrice(30, 2_400_000, 1_680_000)
            )
        )

        // when
        macbook.addAllPrices(targetPrices)

        // then
        assertThat(macbook.prices.macbookPrices).containsExactly(
            targetPrices.macbookPrices[0],
            targetPrices.macbookPrices[1],
            targetPrices.macbookPrices[2]
        )
    }

    @Test
    fun `최근 현재가를 가지고 온다`() {
        // given
        val expectedCurrentPrice: Long = 2_351_000
        macbook.addAllPrices(
            MacbookPrices(
                mutableListOf(
                    MacbookPriceFixture.macbookPrice(2, 2_400_000, expectedCurrentPrice),
                    MacbookPriceFixture.macbookPrice(10, 2_000_000, 1_800_000, 1),
                    MacbookPriceFixture.macbookPrice(30, 2_400_000, 1_680_000, 2)
                )
            )
        )

        // when
        val actual = macbook.findCurrentPrice()

        // then
        assertThat(actual).isEqualTo(expectedCurrentPrice.toBigDecimal())
    }

    @Test
    fun `할인율을 가지고 온다`() {
        // given
        macbook.addAllPrices(
            MacbookPrices(
                mutableListOf(
                    MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000),
                    MacbookPriceFixture.macbookPrice(10, 2_000_000, 1_800_000, 1),
                    MacbookPriceFixture.macbookPrice(30, 2_400_000, 1_680_000, 2)
                )
            )
        )

        // when
        val actual = macbook.findDiscountPercentage()

        // then
        assertThat(actual).isEqualTo(-33)
    }
}
