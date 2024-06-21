package backend.itracker.tracker.config

import backend.itracker.tracker.resolver.AnonymousMemberArgumentResolver
import backend.itracker.tracker.resolver.LoginMemberArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ArgumentResolverConfig(
    private val loginMemberArgumentResolver: LoginMemberArgumentResolver,
    private val anonymousMemberArgumentResolver: AnonymousMemberArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginMemberArgumentResolver)
        resolvers.add(anonymousMemberArgumentResolver)
    }
}
