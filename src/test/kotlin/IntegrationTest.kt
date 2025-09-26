package es.unizar.webeng.hello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `should return home page with modern title and client-side HTTP debug in English`() {
        val response = restTemplate.getForEntity("http://localhost:$port", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("<title>Modern Web App</title>")
        assertThat(response.body).contains("Welcome to Modern Web App")
        assertThat(response.body).contains("Interactive HTTP Testing &amp; Debug")
        assertThat(response.body).contains("Client-Side Educational Tool")
    }

    @Test
    fun `should return home page with modern title and client-side HTTP debug in Spanish`() {
        val response = restTemplate.getForEntity("http://localhost:$port/?lang=es", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("<title>Modern Web App</title>")
        assertThat(response.body).contains("Bienvenido a la Aplicación Web Moderna")
        assertThat(response.body).contains("Pruebas HTTP interactivas y depuración")
        assertThat(response.body).contains("Herramienta educativa del lado del cliente")
    }

    @Test
    fun `should return personalized greeting when name is provided in English`() {
        val response = restTemplate.getForEntity("http://localhost:$port?name=Developer", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("Hello, Developer!")
    }

    @Test
    fun `should return personalized greeting when name is provided in Spanish`() {
        val response = restTemplate.getForEntity("http://localhost:$port?name=Developer&lang=es", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("Hola, Developer!")
    }

    @Test
    fun `should return API response with timestamp in English`() {
        val response = restTemplate.getForEntity("http://localhost:$port/api/hello?name=Test", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
        assertThat(response.body).contains("Hello, Test!")
        assertThat(response.body).contains("timestamp")
    }

    @Test
    fun `should return API response with timestamp in Spanish`() {
        val response = restTemplate.getForEntity("http://localhost:$port/api/hello?name=Test&lang=es", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
        assertThat(response.body).contains("Hola, Test!")
        assertThat(response.body).contains("timestamp")
    }

    @Test
    fun `should serve Bootstrap CSS correctly in English`() {
        val response = restTemplate.getForEntity("http://localhost:$port/webjars/bootstrap/5.3.3/css/bootstrap.min.css", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("body")
        assertThat(response.headers.contentType).isEqualTo(MediaType.valueOf("text/css"))
    }

    @Test
    fun `should serve Bootstrap CSS correctly in Spanish`() {
        val response = restTemplate.getForEntity("http://localhost:$port/webjars/bootstrap/5.3.3/css/bootstrap.min.css?lang=es", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("body")
        assertThat(response.headers.contentType).isEqualTo(MediaType.valueOf("text/css"))
    }

    @Test
    fun `should expose actuator health endpoint in English`() {
        val response = restTemplate.getForEntity("http://localhost:$port/actuator/health", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("UP")
    }

    @Test
    fun `should expose actuator health endpoint in Spanish`() {
        val response = restTemplate.getForEntity("http://localhost:$port/actuator/health?lang=es", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("UP")
    }
    
    @Test
    fun `should display client-side HTTP debug interface in English`() {
        val response = restTemplate.getForEntity("http://localhost:$port?name=Student", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("Interactive HTTP Testing &amp; Debug")
        assertThat(response.body).contains("Client-Side Educational Tool")
        assertThat(response.body).contains("Web Page Greeting")
        assertThat(response.body).contains("API Endpoint")
        assertThat(response.body).contains("Health Check")
        assertThat(response.body).contains("Learning Notes:")
    }

    @Test
    fun `should display client-side HTTP debug interface in Spansish`() {
        val response = restTemplate.getForEntity("http://localhost:$port?name=Student&lang=es", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("Pruebas HTTP interactivas y depuración")
        assertThat(response.body).contains("Herramienta educativa del lado del cliente")
        assertThat(response.body).contains("Saludo de la página web")
        assertThat(response.body).contains("Endpoints de la API")
        assertThat(response.body).contains("Comprobación de salud")
        assertThat(response.body).contains("Notas de aprendizaje:")
    }
}
