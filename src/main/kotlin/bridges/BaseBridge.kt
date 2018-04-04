package bridges

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

open class BaseBridge {
    @PersistenceContext
    lateinit var em: EntityManager
}
