package backend.itracker.tracker.infra.oauth.jwt

import backend.itracker.tracker.infra.oauth.AuthorizationHeader
import backend.itracker.tracker.infra.oauth.exception.OauthRequestException
import backend.itracker.tracker.oauth.OauthId
import backend.itracker.tracker.oauth.OauthServerType
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.IncorrectClaimException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.MissingClaimException
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component

@Component
class JwtDecoder(
    private val jwtConfig: JwtConfig
) {

    private val jwtParser: JwtParser = Jwts.parserBuilder()
        .setSigningKey(jwtConfig.secretKey)
        .requireIssuer(jwtConfig.issuer)
        .build()

    fun parseOauthId(authorizationHeader: AuthorizationHeader): OauthId {
        try {
            val token: String = authorizationHeader.parseHeader()

            val claims = jwtParser.parseClaimsJws(token).body
            return OauthId(claims["serverId"].toString(), OauthServerType.from(claims["type"].toString()))
        } catch (e: SignatureException) {
            throw OauthRequestException("Signature 가 잘못되었습니다.")
        } catch (e: ExpiredJwtException) {
            throw OauthRequestException("토큰이 만료되었습니다.")
        } catch (e: MalformedJwtException) {
            throw OauthRequestException("잘못 생성된 JWT 로 디코딩 할 수 없습니다.")
        } catch (e: MissingClaimException) {
            throw OauthRequestException("JWT 에 기대한 정보를 모두 포함하고 있지 않습니다.")
        } catch (e: IncorrectClaimException) {
            throw OauthRequestException("JWT 에 기대한 정보를 모두 포함하고 있지 않습니다.")
        }
    }
}
