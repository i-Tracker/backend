package backend.itracker.tracker.service.response

data class CoupangLinkResponse(
    val rCode: Int,
    val rMessage: String = "",
    val data: List<Deeplink> = mutableListOf()
)

data class Deeplink(
    val originalUrl: String = "",
    val shortenUrl: String = "",
    val landingUrl: String = ""
)

