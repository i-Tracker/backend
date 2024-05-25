package backend.itracker.crawl.macbook.service

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.repository.MacbookRepository
import backend.itracker.crawl.macbook.fixtures.MacbookFixture
import backend.itracker.crawl.macbook.fixtures.MacbookPriceFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class MacbookServiceTest {

    @Autowired
    lateinit var macbookService: MacbookService

    @Autowired
    lateinit var macbookRepository: MacbookRepository

    private lateinit var macbook: Macbook

    @BeforeEach
    fun setUp() {
        macbook = MacbookFixture.createDefaultMacbookAir()
    }

    @Test
    fun `전체 저장 테스트`() {
        // given
        macbook.addPrice(
            MacbookPriceFixture.macbookPrice(
                discountPercentage = 2,
                basePrice = 2400000,
                currentPrice = 2351000
            )
        )

        // when
        macbookService.saveAll(listOf(macbook))

        // then
        val savedMacbooks = macbookRepository.findAll()
        assertThat(savedMacbooks).hasSize(1)
    }
}

