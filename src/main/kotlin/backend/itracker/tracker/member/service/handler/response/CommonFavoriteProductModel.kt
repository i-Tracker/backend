package backend.itracker.tracker.member.service.handler.response

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

abstract class CommonFavoriteProductModel(
    @JsonIgnore
    val createdAt: LocalDateTime
)
