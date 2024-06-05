package backend.itracker.tracker.service.common

import org.springframework.stereotype.Component
import java.security.GeneralSecurityException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private const val SECRET_ALGORITHM = "HmacSHA256"
private const val DATE_FORMAT = "yyMMdd'T'HHmmss'Z'"

@Component
class HmacGenerator {

    fun generate(
        method: String,
        uri: String,
        secretKey: String,
        accessKey: String,
    ): String {
        val parts = uri.split("\\?")

        if (parts.size > 2) {
            throw IllegalArgumentException("Uri 형식이 잘못되었습니다. uri: $uri")
        }

        val path = parts[0]
        val query = if (parts.size == 2) parts[1] else ""
        val dateFormatGmt = SimpleDateFormat(DATE_FORMAT)
        dateFormatGmt.timeZone = TimeZone.getTimeZone("GMT")
        val datetime = dateFormatGmt.format(Date())
        val message = buildString {
            append(datetime)
            append(method)
            append(path)
            append(query)
        }

        val signature: String
        try {
            val signingKey = SecretKeySpec(secretKey.toByteArray(), SECRET_ALGORITHM)
            val mac: Mac = Mac.getInstance(SECRET_ALGORITHM)
            mac.init(signingKey)
            val rawHmac: ByteArray = mac.doFinal(message.toByteArray())
            signature = HexFormat.of().formatHex(rawHmac)
        } catch (e: GeneralSecurityException) {
            throw IllegalStateException("Hmac을 해싱할 때 예외가 발생했습니다. message = ${e.message}")
        }

        return "CEA algorithm=HmacSHA256, access-key=$accessKey, signed-date=$datetime, signature=$signature"
    }
}
