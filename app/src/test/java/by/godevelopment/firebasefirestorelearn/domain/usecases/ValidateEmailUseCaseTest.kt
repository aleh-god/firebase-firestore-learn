package by.godevelopment.firebasefirestorelearn.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Test

internal class ValidateEmailUseCaseTest {

    val useCases = ValidateEmailUseCase.BaseImpl()

    @Test
    fun `invoke is correct`() {
        val email = "Unit@import.org"
        val exp = useCases(email)
        assertEquals(exp, true)
    }

    @Test
    fun `target string trim is correct`() {
        val email = " Unit@import.org "
        val exp = useCases(email)
        assertEquals(exp, true)
    }

    @Test
    fun `pass null return false`() {
        val email = ""
        val exp = useCases(email)
        assertEquals(exp, false)
    }

    @Test
    fun `pass wrong target return false 1`() {
        val email = "@import.org"
        val exp = useCases(email)
        assertEquals(exp, false)
    }

    @Test
    fun `pass wrong target return false 2`() {
        val email = "import@"
        val exp = useCases(email)
        assertEquals(exp, false)
    }

    @Test
    fun `pass wrong target return false 3`() {
        val email = "Unit@import@org"
        val exp = useCases(email)
        assertEquals(exp, false)
    }
}