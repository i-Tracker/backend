package backend.itracker.tracker.member.service.handler.response

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

abstract class CommonFavoriteProductModel(
    val notificationCount: Long,

    @JsonIgnore
    val createdAt: LocalDateTime
)
