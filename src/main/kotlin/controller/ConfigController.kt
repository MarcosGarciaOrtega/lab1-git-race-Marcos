package es.unizar.webeng.hello.controller

import org.aopalliance.intercept.Interceptor
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale

@Configuration
class ConfigController : WebMvcConfigurer {
    

    @Bean
    fun localeResolver() : LocaleResolver {
        var slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale.ENGLISH)
        return slr
    }

    @Bean
    fun localChangeInterceptor() : LocaleChangeInterceptor {
        var lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }

    override fun addInterceptors(interceptor: InterceptorRegistry) {
        interceptor.addInterceptor(localChangeInterceptor())
    }
}