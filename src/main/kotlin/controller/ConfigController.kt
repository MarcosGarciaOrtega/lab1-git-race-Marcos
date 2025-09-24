package es.unizar.webeng.hello.controller

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale

/*
 * This class is used to configure which language the web application should display.
 */
@Configuration
class ConfigController : WebMvcConfigurer {


    /*
     * Creates a LocaleResolver bean that stores the current language.
     *
     * We use SessionLocaleResolver to store the current user's locale
     * Locale is object which represent regional setting including language and country
     *
     * @return a LocaleResolver with default locale set to ENGLISH
     */
    @Bean
    fun localeResolver() : LocaleResolver {
        var slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale.ENGLISH)
        return slr
    }


    /*
     * Creates a LocaleChangeInterceptor bean.
     *
     * This interceptor allows changing the current locale on every request
     * via a URL parameter (e.g. /?lang=es to switch to Spanish).
     *
     * @return a LocaleChangeInterceptor configured with lang parameter
     */
    @Bean
    fun localChangeInterceptor() : LocaleChangeInterceptor {
        var lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }

    /*
     * We add the interceptor to the application interceptor registry
     *
     * @param interceptor, is the interceptor we want to add to the interceptor registry
     */
    override fun addInterceptors(interceptor: InterceptorRegistry) {
        interceptor.addInterceptor(localChangeInterceptor())
    }
}