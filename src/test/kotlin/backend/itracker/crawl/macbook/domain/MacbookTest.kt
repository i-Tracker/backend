package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.macbook.fixtures.MacbookFixture
import backend.itracker.crawl.macbook.fixtures.MacbookPriceFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.Period
import java.util.stream.Stream

class MacbookTest {

    private lateinit var macbook: Macbook

    @BeforeEach
    fun setUp() {
        macbook = MacbookFixture.createDefaultMacbookAir()
    }

    @Test
    fun `맥북에 가격을 추가한다`() {
        // given
        val targetPrice = MacbookPriceFixture.macbookPrice(2, 2_400_000, 2_351_000)

        // when
        macbook.addPrice(targetPrice)

        // then
        assertThat(macbook.prices.macbookPrices).containsOnly(targetPrice)
    }

    @Test
    fun `맥북에 가격들을 모두 추가한다`() {
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
    fun `평균가를 가지고 온다`() {
        // given
        val firstCurrentPrice: Long = 1_000_000
        val secondCurrentPrice: Long = 800_000
        val thirdCurrentPrice: Long = 600_000
        val expected = (firstCurrentPrice + secondCurrentPrice + thirdCurrentPrice) / 3
        macbook.addAllPrices(
            MacbookPrices(
                mutableListOf(
                    MacbookPriceFixture.macbookPrice(50, 2_000_000, firstCurrentPrice),
                    MacbookPriceFixture.macbookPrice(10, 2_000_000, secondCurrentPrice, 1),
                    MacbookPriceFixture.macbookPrice(30, 2_400_000, thirdCurrentPrice, 2)
                )
            )
        )

        // when
        val actual = macbook.findAveragePrice()

        // then
        assertThat(actual.toLong()).isEqualTo(expected)
    }

    @Test
    fun `역대 최고가를 가지고 온다`() {
        // given
        val allTimeHighPrice: Long = 3_600_000

        macbook.addAllPrices(
            MacbookPrices(
                mutableListOf(
                    MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000),
                    MacbookPriceFixture.macbookPrice(10, 2_000_000, 800_000, 1),
                    MacbookPriceFixture.macbookPrice(30, 2_400_000, allTimeHighPrice, 2)
                )
            )
        )

        // when
        val actual = macbook.findAllTimeHighPrice()

        // then
        assertThat(actual.toLong()).isEqualTo(allTimeHighPrice)
    }

    @Test
    fun `역대 최저가를 가지고 온다`() {
        // given
        val allTimeLowPrice: Long = 100_000

        macbook.addAllPrices(
            MacbookPrices(
                mutableListOf(
                    MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000),
                    MacbookPriceFixture.macbookPrice(10, 2_000_000, 800_000, 1),
                    MacbookPriceFixture.macbookPrice(30, 2_400_000, allTimeLowPrice, 2)
                )
            )
        )

        // when
        val actual = macbook.findAllTimeLowPrice()

        // then
        assertThat(actual.toLong()).isEqualTo(allTimeLowPrice)
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

    @Test
    fun `파트너스 링크를 변경한다`() {
        // given
        val origin = macbook.partnersLink

        // when
        val expected = "changed-link"
        macbook.changePartnersLink(expected)

        // then
        val actual = macbook.partnersLink
        assertAll(
            { assertThat(origin).isNotEqualTo(expected) },
            { assertThat(actual).isEqualTo(expected) }
        )
    }

    @ParameterizedTest(name = "품절 상태가 {0}인지 확인한다")
    @ValueSource(booleans = [true, false])
    fun `품절인지 확인한다`(outOfStockStatus: Boolean) {
        // given
        macbook.addAllPrices(
            MacbookPrices(
                mutableListOf(
                    MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000, outOfStockStatus),
                    MacbookPriceFixture.macbookPrice(10, 2_000_000, 1_800_000, 1),
                    MacbookPriceFixture.macbookPrice(30, 2_400_000, 1_680_000, 2)
                )
            )
        )

        // when
        val actual = macbook.isOutOfStock()

        // then
        assertThat(actual).isEqualTo(outOfStockStatus)
    }

    @ParameterizedTest(name = "{0} {1}")
    @MethodSource("getRecentPricesByPeriod")
    fun `기간에 따라서 값들을 가지고 온다`(period: Period, expectedSize: Int) {
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
        val actual = macbook.getRecentPricesByPeriod(period)

        // then
        assertThat(actual.macbookPrices).hasSize(expectedSize)
    }

    @ParameterizedTest(name = "{0} {1}이 반환된다.")
    @MethodSource("isAllTimeLowPrice")
    fun `역대 최저가를 판단한다`(macbookPrices: MacbookPrices, expected: Boolean) {
        // given
        macbook.addAllPrices(macbookPrices)

        // when
        val actual = macbook.isAllTimeLowPrice()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
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

        @JvmStatic
        private fun isAllTimeLowPrice() = Stream.of(
            Arguments.of(
                named(
                    "오늘이 최저가이면", MacbookPrices(
                        mutableListOf(
                            MacbookPriceFixture.macbookPrice(50, 2_000_000, 1_000_000),
                            MacbookPriceFixture.macbookPrice(10, 2_000_000, 1_800_000, 1),
                            MacbookPriceFixture.macbookPrice(30, 2_400_000, 1_680_000, 2)
                        )
                    )
                ), true
            ),
            Arguments.of(
                named(
                    "오늘이 최저가가 아니면", MacbookPrices(
                        mutableListOf(
                            MacbookPriceFixture.macbookPrice(50, 2_000_000, 2_000_000),
                            MacbookPriceFixture.macbookPrice(10, 2_000_000, 1_800_000, 1),
                            MacbookPriceFixture.macbookPrice(30, 2_400_000, 1_680_000, 2)
                        )
                    )
                ), false
            ),
        )

    }
}

