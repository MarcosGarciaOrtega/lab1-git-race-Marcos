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
    private lateinit var controller: HelloController
    private lateinit var model: Model
    private lateinit var messageSource: MessageSource
    
    @BeforeEach
    fun setup() {
        messageSource = StaticMessageSource().apply{
            addMessage("title.greeting", Locale.ENGLISH, "Welcome to modern web app")
            addMessage("greeting", Locale.ENGLISH, "Hello, {0}")
            addMessage("subtitle.greeting", Locale.ENGLISH, "Welcome to modern web app")
        }
        controller = HelloController(messageSource)
        model = ExtendedModelMap()
        LocaleContextHolder.setDefaultLocale(Locale.ENGLISH)
    }
    
    @Test
    fun `should return welcome view with default message`() {
        val view = controller.welcome(model, "")

        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Welcome to modern web app")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message`() {
        val view = controller.welcome(model, "Developer")

        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hello, Developer")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with timestamp`() {
        val apiController = HelloApiController(messageSource)
        val response = apiController.helloApi("Test")

        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Hello, Test")
        assertThat(response["timestamp"]).isNotNull()
    }
}
