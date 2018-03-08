package dao

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

abstract class BaseDao {
    @PersistenceContext
    lateinit var em: EntityManager
}
