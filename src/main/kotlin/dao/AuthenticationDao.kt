package dao

import domain.User
import org.mindrot.jbcrypt.BCrypt
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class AuthenticationDao {
    @PersistenceContext
    lateinit var em: EntityManager

    private val salt = "IAmOSoSaltyAboutJea"

    /**
     * Attempts to login a user by username and password
     */
    fun login(username: String, password: String): User? {
        var user = User.getByUsername(username, em) ?: return null

        if (BCrypt.hashpw(password, salt) == user.password) return user
        return null
    }

    /**
     * Creates a new user
     */
    fun create(username: String, password: String, email: String) = User(
        null,
        username,
        email,
        BCrypt.hashpw(password, salt),
        emptyList()
    )
}
