package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.macbook.fixtures.MacbookPriceFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Period
import java.util.stream.Stream

class MacbookPricesTest {

    private lateinit var macbookPrices: MacbookPrices
    private var allTimeLowPrice: Long = 0
    private var allTimeHighPrice: Long = 0

    @BeforeEach
    fun setUp() {
        allTimeLowPrice = 1_000_000
        allTimeHighPrice = 1_800_000
        macbookPrices = MacbookPrices(
            mutableListOf(
                MacbookPriceFixture.macbookPrice(50, 2_000_000, allTimeLowPrice, 1),
                MacbookPriceFixture.macbookPrice(20, 2_000_000, 1_600_000, 2),
                MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000, 3),
                MacbookPriceFixture.macbookPrice(20, 2_000_000, 1_600_000, 4),
                MacbookPriceFixture.macbookPrice(10, 2_000_000, allTimeHighPrice, 5)
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

    @Test
    fun `평균 가격을 구한다`() {
        // given
        val macbookPricesValues = macbookPrices.macbookPrices
        val expected = macbookPricesValues.sumOf { it.currentPrice }.toLong() / macbookPricesValues.size

        // when
        val actual = macbookPrices.findAveragePrice()

        // then
        assertThat(actual.toLong()).isEqualTo(expected)
    }

    @Test
    fun `역대 최고가를 구한다`() {
        // when
        val actual = macbookPrices.findAllTimeHighPrice()

        // then
        assertThat(actual).isEqualTo(allTimeHighPrice.toBigDecimal())
    }

    @Test
    fun `역대 최저가를 구한다`() {
        // when
        val actual = macbookPrices.findAllTimeLowPrice()

        // then
        assertThat(actual).isEqualTo(allTimeLowPrice.toBigDecimal())
    }

    @ParameterizedTest(name = "가격의 outOfStock이 {1}이면 {1}이 나온다")
    @MethodSource("isOutOfStock")
    fun `품절 여부를 확인한다`(price: MacbookPrice, expected: Boolean) {
        // given
        macbookPrices.add(price)

        // when
        val actual = macbookPrices.isOutOfStock()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("isAllTimeLowPrice")
    fun `역대 최저가인지 확인한다`(price: MacbookPrice, expected: Boolean) {
        // given
        macbookPrices.add(price)

        // when
        val actual = macbookPrices.isAllTimeLowPrice()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "{0} {1}")
    @MethodSource("getRecentPricesByPeriod")
    fun `기간별로 가격을 확인한다`(period: Period, expectedSize: Int) {
        // given
        macbookPrices.add(MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000))

        // when
        val actual = macbookPrices.getRecentPricesByPeriod(period)

        // then
        assertThat(actual.macbookPrices).hasSize(expectedSize)
    }

    companion object {
        @JvmStatic
        fun isOutOfStock() = Stream.of(
            Arguments.of(MacbookPriceFixture.macbookPrice(10, 100_000, 90_000, true), true),
            Arguments.of(MacbookPriceFixture.macbookPrice(10, 100_000, 90_000, false), false)
        )

        @JvmStatic
        fun isAllTimeLowPrice() = Stream.of(
            Arguments.of(MacbookPriceFixture.macbookPrice(90, 100_000, 10_000), true),
            Arguments.of(MacbookPriceFixture.macbookPrice(10, 5_000_000, 4_500_000), false)
        )

        @JvmStatic
        private fun getRecentPricesByPeriod(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    named("1일치를 조회하면", Period.of(0, 0, 1)),
                    named("1개만 조회된다", 1)
                ),

                Arguments.of(
                    named("2일치를 조회하면", Period.of(0, 0, 2)),
                    named("2개만 조회된다", 2)
                ),
            )
        }
    }
}
