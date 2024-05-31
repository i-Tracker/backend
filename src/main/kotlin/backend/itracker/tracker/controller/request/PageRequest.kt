package backend.itracker.tracker.controller.request

class PageRequest {
    var page: Int = 0
    var limit: Int = 8

    constructor(page: Int, limit: Int) {
        require(page >= 1) { "Page must be greater than 0" }
        require(limit >= 1) { "Limit must be greater than 0" }

        if (page >= 1) {
            this.page = page - 1
        }

        this.limit = limit
    }
}



