package utils

import org.junit.Test
import kotlin.test.assertEquals

class DigestUtilsKtTest {
    @Test
    fun testsha() {
        val steveHashed = "f148389d080cfe85952998a8a367e2f7eaf35f2d72d2599a5b0412fe4094d65c"

        assertEquals(steveHashed, sha256("steve"))
    }
}
