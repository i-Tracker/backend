package backend.itracker.crawl.macbook.fixtures

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory

abstract class MacbookFixture {

    companion object {
        fun macbook(
            category: MacbookCategory,
            size: Int,
            chip: String,
            cpu: String,
            gpu: String,
            storage: String,
            memory: String,
            color: String
        ): Macbook {
            return Macbook(
                coupangId = 1,
                company = "Apple",
                name = "Apple 2024 맥북 에어 13 M3, 미드나이트, M3 8코어, 10코어 GPU, 1TB, 16GB, 35W 듀얼, 한글",
                category = category,
                chip = chip,
                cpu = cpu,
                gpu = gpu,
                storage = storage,
                memory = memory,
                language = "한글",
                color = color,
                size = size,
                releaseYear = 2024,
                productLink = "https://www.coupang.com/vp/products/7975088162?itemId=22505523317&vendorItemId=89547640201&sourceType=cmgoms&omsPageId=84871&omsPageUrl=84871",
                thumbnail = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/15943638430632-244768e7-86d1-4484-b772-013d185666b8.jpg",
            )
        }

        fun createDefaultMacbookAir(): Macbook {
            return macbook(
                category = MacbookCategory.MACBOOK_AIR,
                chip = "M3",
                cpu = "8코어",
                gpu = "10코어",
                storage = "1TB",
                memory = "16GB",
                color = "미드나이트",
                size = 13
            )
        }
    }
}
