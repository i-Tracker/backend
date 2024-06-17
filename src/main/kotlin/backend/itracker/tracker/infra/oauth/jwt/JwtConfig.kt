package backend.itracker.tracker.infra.oauth.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import java.security.Key

@Profile("!test")
@ConfigurationProperties(prefix = "jwt.token")
data class JwtConfig(
    private val secret: String,
    val issuer: String,
    val expireMinutes: Int
) {

    val secretKey: Key
        get() = Keys.hmacShaKeyFor(secret.toByteArray())
}
