package util

import io.restassured.RestAssured
import io.restassured.response.Validatable
import io.restassured.response.ValidatableResponseOptions
import io.restassured.specification.RequestSender
import io.restassured.specification.RequestSpecification

// From https://gist.github.com/outofcoffee/6afd3e211b6ca09bb8e423fa1bc31f2a
fun given(block: RequestSpecification.() -> Unit): RequestSpecification = RestAssured.given().apply(block)

fun RequestSpecification.jsonBody(body: Any): RequestSender = this.contentType(io.restassured.http.ContentType.JSON).body(body)
fun RequestSpecification.on(block: RequestSender.() -> Unit): RequestSender = this.`when`().apply(block)
infix fun Validatable<*, *>.itHas(block: ValidatableResponseOptions<*, *>.() -> Unit): ValidatableResponseOptions<*, *> = this.then().apply(block)
