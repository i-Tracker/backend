package backend.itracker.crawl.macbook.service

import backend.itracker.crawl.common.PartnersLinkInfo
import backend.itracker.crawl.config.ServiceTestConfig
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.macbook.domain.repository.findByIdAllFetch
import backend.itracker.crawl.macbook.fixtures.MacbookFilterConditionFixture
import backend.itracker.crawl.macbook.fixtures.MacbookFixture
import backend.itracker.crawl.macbook.fixtures.MacbookPriceFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test


class MacbookServiceTest : ServiceTestConfig() {

    @Test
    fun `없는 맥북을 저장한다`() {
        // given
        val first = MacbookFixture.macbook(MacbookCategory.MACBOOK_PRO, 1)
        val second = MacbookFixture.macbook(MacbookCategory.MACBOOK_PRO, 2)

        // when
        macbookService.saveAll(listOf(first, second))

        // then
        val savedMacbooks = macbookRepository.findAll()
        assertThat(savedMacbooks).hasSize(2)
    }

    @Test
    fun `이미 있는 맥북의 가격을 추가한다`() {
        // given
        val first = saveMacbook(MacbookFixture.macbook(MacbookCategory.MACBOOK_AIR, 1))
        val second = saveMacbook(MacbookFixture.macbook(MacbookCategory.MACBOOK_PRO, 2))
        val firstMacbookPrice = MacbookPriceFixture.macbookPrice(10, 100_000, 90_000)
        val secondMacbookPrice = MacbookPriceFixture.macbookPrice(10, 100_000, 90_000)
        first.addPrice(firstMacbookPrice)
        second.addPrice(secondMacbookPrice)

        // when
        macbookService.saveAll(listOf(first, second))

        // then
        val firstMacbook = macbookRepository.findByIdAllFetch(first.id)
        val secondMacbook = macbookRepository.findByIdAllFetch(second.id)
        assertAll({ assertThat(firstMacbook.prices.macbookPrices).containsOnly(firstMacbookPrice) },
            { assertThat(secondMacbook.prices.macbookPrices).containsOnly(secondMacbookPrice) })
    }

    @Test
    fun `맥북의 파트너스 링크를 변경한다`() {
        // given
        val firstMacbook = saveMacbook(MacbookFixture.macbook(1, "first partners link"))
        val secondMacbook = saveMacbook(MacbookFixture.macbook(2, "second partners link"))

        val firstPartnersLink = "new partners link 1"
        val secondPartnersLink = "new partners link 2"
        val partnersLinkInformation = listOf(
            PartnersLinkInfo(firstMacbook.productLink, firstPartnersLink),
            PartnersLinkInfo(secondMacbook.productLink, secondPartnersLink)
        )

        // when
        macbookService.updateAllPartnersLink(partnersLinkInformation)

        // then
        assertThat(macbookRepository.findAll().map { it.partnersLink }.toList()).containsExactly(
            firstPartnersLink, secondPartnersLink
        )
    }

    @Test
    fun `카테고리가 일치하는 맥북을 모두 fetch join 한다`() {
        // given
        val macbookAir1 = saveMacbook(MacbookFixture.macbook(MacbookCategory.MACBOOK_AIR, 1))
        val macbookAir2 = saveMacbook(MacbookFixture.macbook(MacbookCategory.MACBOOK_AIR, 2))
        val macbookAir3 = saveMacbook(MacbookFixture.macbook(MacbookCategory.MACBOOK_AIR, 3))
        val macbookPro1 = saveMacbook(MacbookFixture.macbook(MacbookCategory.MACBOOK_PRO, 4))
        val macbookPro2 = saveMacbook(MacbookFixture.macbook(MacbookCategory.MACBOOK_PRO, 5))

        // when
        val macbookAirs = macbookService.findAllFetchByCategory(MacbookCategory.MACBOOK_AIR)
        val macbookPros = macbookService.findAllFetchByCategory(MacbookCategory.MACBOOK_PRO)

        // then
        assertAll(
            { assertThat(macbookAirs).containsExactly(macbookAir1, macbookAir2, macbookAir3) },
            { assertThat(macbookPros).containsExactly(macbookPro1, macbookPro2) }
        )
    }

    @Test
    fun `카테고리와 필터가 일치한 맥북을 찾는다`() {
        // given
        val macbookAir1 = saveMacbook(MacbookFixture.macbook(1, MacbookCategory.MACBOOK_AIR, size = 13))
        val macbookAir2 = saveMacbook(MacbookFixture.macbook(2, MacbookCategory.MACBOOK_AIR, chip = "M1"))
        val macbookAir3 = saveMacbook(MacbookFixture.macbook(3, MacbookCategory.MACBOOK_AIR, storage = "256GB"))
        val macbookAir4 = saveMacbook(MacbookFixture.macbook(4, MacbookCategory.MACBOOK_AIR, memory = "16GB"))
        val macbookAir5 = saveMacbook(MacbookFixture.macbook(5, MacbookCategory.MACBOOK_AIR, color = "실버"))

        // when
        // then
        assertAll(
            {
                assertThat(macbookService.findAllByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(size = 13)
                ).map { it.coupangId }).containsExactly(macbookAir1.coupangId)
            },
            {
                assertThat(macbookService.findAllByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(processor = "M1")
                ).map { it.coupangId }).containsExactly(macbookAir2.coupangId)
            },
            {
                assertThat(macbookService.findAllByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(storage = "256GB")
                ).map { it.coupangId }).containsExactly(macbookAir3.coupangId)
            },
            {
                assertThat(macbookService.findAllByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(memory = "16GB")
                ).map { it.coupangId }).containsExactly(macbookAir4.coupangId)
            },
            {
                assertThat(macbookService.findAllByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(color = "실버")
                ).map { it.coupangId }).containsExactly(macbookAir5.coupangId)
            }
        )
    }

    @Test
    fun `카테고리와 필터가 일치한 맥북을 fetch jion 한다`() {
        // given
        val macbookAir1 = saveMacbook(MacbookFixture.macbook(1, MacbookCategory.MACBOOK_AIR, size = 13))
        val macbookAir2 = saveMacbook(MacbookFixture.macbook(2, MacbookCategory.MACBOOK_AIR, chip = "M1"))
        val macbookAir3 = saveMacbook(MacbookFixture.macbook(3, MacbookCategory.MACBOOK_AIR, storage = "256GB"))
        val macbookAir4 = saveMacbook(MacbookFixture.macbook(4, MacbookCategory.MACBOOK_AIR, memory = "16GB"))
        val macbookAir5 = saveMacbook(MacbookFixture.macbook(5, MacbookCategory.MACBOOK_AIR, color = "실버"))

        // when
        // then
        assertAll(
            {
                assertThat(macbookService.findAllFetchByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(size = 13)
                ).map { it.coupangId }).containsExactly(macbookAir1.coupangId)
            },
            {
                assertThat(macbookService.findAllFetchByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(processor = "M1")
                ).map { it.coupangId }).containsExactly(macbookAir2.coupangId)
            },
            {
                assertThat(macbookService.findAllFetchByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(storage = "256GB")
                ).map { it.coupangId }).containsExactly(macbookAir3.coupangId)
            },
            {
                assertThat(macbookService.findAllFetchByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(memory = "16GB")
                ).map { it.coupangId }).containsExactly(macbookAir4.coupangId)
            },
            {
                assertThat(macbookService.findAllFetchByCategoryAndFilter(
                    MacbookCategory.MACBOOK_AIR,
                    MacbookFilterConditionFixture.create(color = "실버")
                ).map { it.coupangId }).containsExactly(macbookAir5.coupangId)
            }
        )
    }

    @Test
    fun `id로 맥북을 fetch join 한다`() {
        // given
        val expected = saveMacbook(MacbookFixture.default())
        val savedId = expected.id

        // when
        val actual = macbookService.findMacbookById(savedId)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `id 범위로 맥북을 찾는다`() {
        // given
        val macbook1 = saveMacbook(MacbookFixture.default())
        val startMacbook = saveMacbook(MacbookFixture.default())
        val endMacbook = saveMacbook(MacbookFixture.default())
        val macbook4 = saveMacbook(MacbookFixture.default())

        // when
        val actual = macbookService.findByIdBetween(startMacbook.id, endMacbook.id)

        // then
        assertThat(actual).containsExactly(startMacbook, endMacbook)
    }
}
