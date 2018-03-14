package bridges

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.mockito.Mockito
import javax.persistence.EntityManager

class ProfileModelDaoTest {
    lateinit var profileDao: ProfileBridge

    @Before
    fun setup() {
        val em = mock<EntityManager> {}

        Mockito.doNothing().`when`(em).persist(any())

        profileDao = ProfileBridge()
        profileDao.em = em
    }
}
