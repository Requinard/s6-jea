package utils

fun link(method: Methods, url: String, parameters: Map<String, String> = mapOf() , body: Map<String, String> = emptyMap()) = mapOf<String, Any>(
    "method" to method.name,
    "url" to url,
    "body" to body
)

enum class Methods {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE
}
