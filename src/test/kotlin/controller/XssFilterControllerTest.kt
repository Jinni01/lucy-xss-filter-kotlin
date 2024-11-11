package controller

import com.jinni01.LucyXssFilterKotlinApplication
import com.jinni01.dto.JsonBodyTestDto
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType

@SpringBootTest(classes = [LucyXssFilterKotlinApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class XssFilterControllerTest(
    @LocalServerPort
    private val port: Int
) : AnnotationSpec() {

    init {
        RestAssured.port = port
    }

    @Test
    fun filterRequestParam() {
        RestAssured
            .given()
                .queryParam(PARAM_VALUE, ORIGIN_CONTENTS)
            .`when`()
                .get("/filter-test/mappings/get/simple-string-parameter")
            .then()
                .extract()
                .asString()
                .shouldBe(FILTERED_CONTENTS)
    }

    @Test
    fun filterRequestBodyJson() {
        RestAssured
            .given()
                .body(JsonBodyTestDto(ORIGIN_CONTENTS))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .`when`()
                .post("/filter-test/mappings/post/simple-json-body")
            .then()
                .extract()
                .path<String>(FIELD_PROPERTY)
                .shouldBe(FILTERED_CONTENTS)
    }

    companion object {
        const val PARAM_VALUE = "param"
        const val FIELD_PROPERTY = "property"
        const val ORIGIN_CONTENTS = "<script>alert(document.cookie);</script>"
        const val FILTERED_CONTENTS = "&lt;script&gt;alert(document.cookie);&lt;/script&gt;"
    }
}