package utils

import org.junit.Test
import kotlin.test.assertEquals

internal class _TimeUtilsKtTest {

    @Test
    fun testNow() {
        val reallyNow = now()

        assertEquals(reallyNow.toLocalDateTime().year, 2018)
    }
}
