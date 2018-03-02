package domain

import javax.persistence.*

@Entity(name = "User")
@NamedQuery(name = "User.getByUsername", query = "select u from user u where u.username LIKE :username")
data class User(
    @Id
    @GeneratedValue
    var id: Int?,
    var username: String,
    var email: String,
    var password: String,
    @ManyToMany(mappedBy = "users")
    var groups: List<KwetterGroup>
) {
    companion object {
        fun getByUsername(username: String, em: EntityManager): User? = em.createNamedQuery("User.getByUsername", User::class.java)
            .setParameter("username", username)
            .resultList
            .firstOrNull()
    }
}
