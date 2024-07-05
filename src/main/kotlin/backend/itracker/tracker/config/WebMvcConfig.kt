package backend.itracker.tracker.config

import backend.itracker.tracker.common.converter.OauthServerTypeConverter
import backend.itracker.tracker.common.converter.ProductFilterCategoryConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(ProductFilterCategoryConverter())
        registry.addConverter(OauthServerTypeConverter())
    }
}
