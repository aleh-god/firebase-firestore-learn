package by.godevelopment.firebasefirestorelearn.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Test

internal class ValidatePasswordUseCaseTest {

    val useCases = ValidatePasswordUseCase.BaseImpl()

    @Test
    fun `invoke is correct`() {
        val password = "Unit@import.org".toCharArray()
        val exp = useCases(password)
        assertEquals(exp, true)
    }

    @Test
    fun `invoke get null return false`() {
        val password = "".toCharArray()
        val exp = useCases(password)
        assertEquals(exp, false)
    }

    @Test
    fun `invoke get password less 4 letters return false`() {
        val password = "org".toCharArray()
        val exp = useCases(password)
        assertEquals(exp, false)
    }

    @Test
    fun `invoke get password with space letters return false`() {
        val password = "Unit import.org".toCharArray()
        val exp = useCases(password)
        assertEquals(exp, false)
    }
}
