package it.mbkj.kuangji

import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.math.MathContext

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var a=1.1
       var b =1.15
        print(BigDecimal(b).subtract(BigDecimal(a), MathContext(BigDecimal.ROUND_HALF_UP)))
        assertEquals(4, 2 + 2)
    }
}
