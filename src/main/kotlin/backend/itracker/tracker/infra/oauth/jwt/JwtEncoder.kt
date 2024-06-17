package backend.itracker.tracker.infra.oauth.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Profile("!test")
@Component
class JwtEncoder(
    private val jwtConfig: JwtConfig
) {

    fun issueJwtToken(payload: Map<String, Any>): String {
        val now = Date()
        val expiration = Date(now.time + Duration.ofMinutes(jwtConfig.expireMinutes.toLong()).toMillis())
        val claims = Jwts.claims()

        return Jwts.builder()
            .signWith(jwtConfig.secretKey, SignatureAlgorithm.HS256)
            .setIssuer(jwtConfig.issuer)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .addClaims(claims)
            .addClaims(payload)
            .compact()
    }
}
