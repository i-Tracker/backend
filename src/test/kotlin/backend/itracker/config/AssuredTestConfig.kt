package backend.itracker.config

import backend.itracker.tracker.common.PathParams
import backend.itracker.tracker.common.QueryParams
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType


class AssuredTestConfig : ServiceTestConfig() {

    @BeforeEach
    fun assuredTestSetUp(@LocalServerPort port: Int) {
        RestAssured.port = port
    }

    fun get(url: String) = given()
        .`when`().get(url)
        .then().log().ifError()
        .extract()

    fun get(url: String, pathParams: PathParams) = given()
        .pathParams(pathParams.values)
        .`when`().get(url)
        .then().log().ifError()
        .extract()

    fun get(url: String, pathParams: PathParams, queryParams: QueryParams) = given()
        .pathParams(pathParams.values)
        .queryParams(queryParams.values)
        .`when`().get(url)
        .then().log().ifError()
        .extract()

    private fun given() = RestAssured.given().log().ifValidationFails()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
}
