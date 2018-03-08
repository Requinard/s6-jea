package util

import org.junit.Test
import kotlin.test.assertEquals

internal class Util_TimeUtilsKtTest {

    @Test
    fun testNow() {
        val reallyNow = now()

        assertEquals(reallyNow.toLocalDateTime().year, 2018)
    }
}
