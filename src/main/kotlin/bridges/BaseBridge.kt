package bridges

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

abstract class BaseBridge {
    @PersistenceContext
    lateinit var em: EntityManager
}
