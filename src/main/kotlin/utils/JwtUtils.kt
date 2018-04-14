package utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import models.UserModel
import org.joda.time.DateTime
import services.UserService
import javax.ejb.Stateless
import javax.inject.Inject
import javax.inject.Named
import javax.ws.rs.core.HttpHeaders

private val algorithm by lazy { Algorithm.HMAC256("Waarom is java EE zo'n absolute  kutzooi? Ik snap er echt geen flikker van") }
private val issuer = "kwetter-widlfly"
private val verifier = JWT.require(algorithm).withIssuer(issuer).build()

fun isValidJwt(token: String) = try {
    verifier.verify(token) != null
} catch (ex: Exception) {
    false
}

@Named
@Stateless
class JwtUtils @Inject constructor(val userService: UserService) {

    private val today get() = DateTime.now().toDate()
    private val oneMonthFromNow get() = DateTime.now().plusMonths(1).toDate()

    /**
     * Creates a signed JWT token
     * @param userId user Id to be encoded inputs JWT
     * @param username username to be encoded inputs JWT
     * @param groups A list of groups to be encoded inputs JWT
     * @return A signed and verified JWT with one month validity
     */
    fun createToken(
        userId: String = "",
        username: String = "",
        groups: Array<String> = emptyArray()
    ) = JWT.create()
        .withIssuer(issuer)
        .withClaim("id", userId)
        .withClaim("username", username)
        .withArrayClaim("groups", groups)
        .withIssuedAt(today)
        .withExpiresAt(oneMonthFromNow)
        .sign(algorithm)

    /**
     * Verifies an incoming token
     * @param token A JWT token to be verified
     */
    fun verifyToken(token: String): DecodedJWT? = try {
        verifier.verify(token)
    } catch (ex: Exception) {
        null
    }

    /**
     * Authenticate a user and create a JWT token
     * @param username username to log inputs with
     * @param password unhashed password to check
     * @return Valid JWT. If user does not exists or password does not match null is returned
     */
    fun login(username: String, password: String): String? {
        val user = userService.getByUsername(username) ?: return null
        // If user is not found return null

        if (sha256(password) != user.password) return null
        // If password does not match return null

        val groups = user.groups
            .map { it.groupname }
            .toTypedArray()

        return createToken(user.id.toString(), user.username, groups)
    }

    /**
     * Check if there's a logged inputs user
     * @param token JWT token
     */
    fun isLoggedIn(token: String) = verifyToken(token) != null

    /**
     * Get the currently logged inputs user
     * @param token JWT token
     * @return Logged inputs user. Null when user is not present or token is invalid
     */
    fun loggedInUser(token: String): UserModel {
        val verifiedToken = verifyToken(token) ?: throw Exception("There was no verifiable token!")

        val username = verifiedToken.getClaim("username").asString()

        return userService.getByUsername(username) ?: throw Exception("There was no user when there  has to be a user!")
    }

    fun loggedInUser(header: HttpHeaders): UserModel {
        val authHeader = header.getHeaderString(HttpHeaders.AUTHORIZATION).substring("Bearer".length).trim()

        return loggedInUser(authHeader)
    }

    /**
     * Check if the currently logged inputs user is part of a group
     * @param token JWT token
     * @param groupname Groupname to check against
     * @return If user inputs group. If no user present default to false
     */
    fun partOfGroup(token: String, groupname: String) = verifyToken(token)
        ?.getClaim("groups")
        ?.asArray(String::class.java)
        ?.any { it == groupname } ?: false
}
