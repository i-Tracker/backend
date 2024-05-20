package backend.itracker.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpMethod.PATCH
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PUT
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    @Value("\${cors.allowed-origins}")
    lateinit var allowedOrigins: List<String>

    override fun addCorsMappings(registry: CorsRegistry) {
        val origins = allowedOrigins.toTypedArray()
        registry.addMapping("/**")
            .allowedOrigins(*origins)
            .allowCredentials(true)
            .allowedMethods(GET.name(), POST.name(), PUT.name(), PATCH.name(), DELETE.name(), OPTIONS.name())
            .exposedHeaders(HttpHeaders.LOCATION, HttpHeaders.AUTHORIZATION)
            .maxAge(3600)
    }
}
