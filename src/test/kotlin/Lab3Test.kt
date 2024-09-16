import dev.fobo66.crypto.MD5
import dev.fobo66.crypto.SHA1
import java.security.MessageDigest
import kotlin.test.Test
import kotlin.test.assertEquals

class Lab3Test {
    @Test
    fun `MD5 hash is correct`() {
        val md5 = MD5()
        val hash = md5.compute(CLEARTEXT.toByteArray())
        val expectedHash = MessageDigest.getInstance("MD5").digest(CLEARTEXT.toByteArray())
        assertEquals(expectedHash.toString(Charsets.UTF_8), hash.toString(Charsets.UTF_8))
    }

    @Test
    fun `SHA1 hash is correct`() {
        val sha1 = SHA1()
        val hash = sha1.compute(CLEARTEXT.toByteArray())
        val expectedHash = MessageDigest.getInstance("SHA1").digest(CLEARTEXT.toByteArray())
        assertEquals(expectedHash.toString(Charsets.UTF_8), hash.toString(Charsets.UTF_8))
    }

    companion object {
        private const val CLEARTEXT = "test"
    }
}
