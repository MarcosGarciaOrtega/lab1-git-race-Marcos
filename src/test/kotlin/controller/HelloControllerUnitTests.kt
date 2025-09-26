package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.StaticMessageSource
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap
import java.util.Locale

class HelloControllerUnitTests {
    private lateinit var englishController: HelloController
    private lateinit var spanishController: HelloController
    private lateinit var model: Model
    private lateinit var messageSourceEnglish: MessageSource
    private lateinit var messageSourceSpanish: MessageSource
    
    @BeforeEach
    fun setup() {
        messageSourceEnglish = StaticMessageSource().apply{
            addMessage("title.greeting", Locale.ENGLISH, "Welcome to modern web app")
            addMessage("greeting", Locale.ENGLISH, "Hello, {0}")
            addMessage("subtitle.greeting", Locale.ENGLISH, "Welcome to modern web app")
        }
        messageSourceSpanish = StaticMessageSource().apply{
            addMessage("title.greeting", Locale("es"), "Bienvenido a la Aplicación Web Moderna")
            addMessage("greeting", Locale("es"), "Hola, {0}")
            addMessage("subtitle.greeting", Locale( "es"), "Bienvenido a la Aplicación Web Moderna")
        }
        englishController = HelloController(messageSourceEnglish)
        spanishController = HelloController(messageSourceSpanish)
        model = ExtendedModelMap()

    }
    
    @Test
    fun `should return welcome view with default message in English`() {
        LocaleContextHolder.setDefaultLocale(Locale.ENGLISH) //We change the LocalContextHolder for each language we are testing
        val view = englishController.welcome(model, "")

        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Welcome to modern web app")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }

    @Test
    fun `should return welcome view with default message in Spanish`() {
        LocaleContextHolder.setDefaultLocale(Locale("es"))
        val view = spanishController.welcome(model, "")

        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Bienvenido a la Aplicación Web Moderna")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message in English`() {
        LocaleContextHolder.setDefaultLocale(Locale.ENGLISH)
        val view = englishController.welcome(model, "Developer")

        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hello, Developer")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }

    @Test
    fun `should return welcome view with personalized message in Spanish`() {
        LocaleContextHolder.setDefaultLocale(Locale("es"))
        val view = spanishController.welcome(model, "Developer")


        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hola, Developer")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with timestamp in English`() {
        LocaleContextHolder.setDefaultLocale(Locale.ENGLISH)
        val apiController = HelloApiController(messageSourceEnglish)
        val response = apiController.helloApi("Test")

        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Hello, Test")
        assertThat(response["timestamp"]).isNotNull()
    }

    @Test
    fun `should return API response with timestamp in Spanish`() {
        LocaleContextHolder.setDefaultLocale(Locale("es"))
        val apiController = HelloApiController(messageSourceSpanish)
        val response = apiController.helloApi("Test")


        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Hola, Test")
        assertThat(response["timestamp"]).isNotNull()
    }
}
