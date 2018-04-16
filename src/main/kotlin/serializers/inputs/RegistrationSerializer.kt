package serializers.inputs

data class RegistrationSerializer(
    val username: String,
    val password: String,
    val email: String
)
