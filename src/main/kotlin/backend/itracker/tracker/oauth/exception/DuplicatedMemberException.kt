package backend.itracker.tracker.oauth.exception

import backend.itracker.exception.ClientException
import backend.itracker.exception.code.ErrorCode

class DuplicatedMemberException(
    override val errorCode: ErrorCode = ErrorCode.DUPLICATED_MEMBER_EXCEPTION,
    override val message: String
) : ClientException(errorCode, message)
