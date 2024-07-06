package backend.itracker.exception

import backend.itracker.exception.code.ErrorCode

abstract class ClientException(
    open val errorCode: ErrorCode,
    override val message: String
) : RuntimeException(message)
