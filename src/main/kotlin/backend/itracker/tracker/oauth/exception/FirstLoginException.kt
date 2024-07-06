package backend.itracker.tracker.oauth.exception

import backend.itracker.exception.ClientException
import backend.itracker.exception.code.ErrorCode

data class FirstLoginException(
    override val errorCode: ErrorCode = ErrorCode.FIRST_LOGIN_EXCEPTION,
    override val message: String
) : ClientException(errorCode, message)
