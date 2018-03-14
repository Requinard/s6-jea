package interceptors

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import interceptors.bindings.CensorKweetInterceptor
import org.junit.Before
import org.junit.Test
import javax.interceptor.InvocationContext
import kotlin.test.assertNotEquals

internal class KweetModelInterceptorTest {
    lateinit var icMock: InvocationContext
    val interceptor = CensorKweetInterceptor()

    val params = arrayOf<Any>("oracle")

    @Before
    fun setup() {
        icMock = mock {
            on { parameters } doReturn params
            on { proceed() } doReturn Any()
        }
    }

    @Test
    fun censorKweet() {
        interceptor.censor(icMock)

        assertNotEquals("oracle", params[0] as String)
    }
}
