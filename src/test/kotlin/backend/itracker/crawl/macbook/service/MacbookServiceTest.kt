package backend.itracker.crawl.macbook.service

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookPrice
import backend.itracker.crawl.macbook.domain.MacbookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
class MacbookServiceTest {

    @Autowired
    lateinit var macbookService: MacbookService

    @Autowired
    lateinit var macbookRepository: MacbookRepository

    @Test
    fun saveAll() {
        // given
        val macbook = Macbook(
            company = "Apple",
            name = "Apple 2024 맥북 에어 13 M3, 미드나이트, M3 8코어, 10코어 GPU, 1TB, 16GB, 35W 듀얼, 한글",
            type = "맥북 에어",
            cpu = "M3 8코어",
            gpu = "10코어 GPU",
            storage = "1TB",
            memory = "16GB",
            language = "한글",
            color = "미드나이트",
            size = 13,
            releaseYear = 2024,
            productLink = "https://www.coupang.com/vp/products/7975088162?itemId=22505523317&vendorItemId=89547640201&sourceType=cmgoms&omsPageId=84871&omsPageUrl=84871",
            thumbnail = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/15943638430632-244768e7-86d1-4484-b772-013d185666b8.jpg"
        )
        macbook.addPrice(
            MacbookPrice(
                discountPercentage = 2,
                basePrice = BigDecimal.valueOf(2400000),
                discountPrice = BigDecimal.valueOf(2351000)
            )
        )
        val macbooks = listOf(
            macbook
        )

        // when
        macbookService.saveAll(macbooks)

        // then
        val savedMacbooks = macbookRepository.findAll()
        assertThat(savedMacbooks).hasSize(1)
    }
}

