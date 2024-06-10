package backend.itracker.tracker.service.request

data class DeepLinkRequest(
    val coupangUrls: List<String>,
    val subId: String = "itracker"
)
