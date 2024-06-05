package backend.itracker.tracker.service.request

data class CoupangLinkRequest(
    val coupangUrls: List<String>,
    val subId: String = "itracker"
)
