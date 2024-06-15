package backend.itracker.tracker.controller.converter

import backend.itracker.tracker.oauth.OauthServerType
import org.springframework.core.convert.converter.Converter

class OauthServerTypeConverter: Converter<String, OauthServerType> {

    override fun convert(source: String): OauthServerType {
        return OauthServerType.from(source)
    }
}
