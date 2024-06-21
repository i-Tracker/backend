package backend.itracker.tracker.resolver

import backend.itracker.tracker.infra.oauth.AuthorizationHeader
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.oauth.service.OauthService
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AnonymousMemberArgumentResolver(
    private val oauthService: OauthService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AnonymousMember::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Member {
        val authorization = webRequest.getHeader(AUTHORIZATION) ?: return Member.anonymous()

        return oauthService.findByOauthId(AuthorizationHeader(authorization))
    }
}
