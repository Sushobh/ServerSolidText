import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordTest {

    private val passwordEncoder = BCryptPasswordEncoder(16)

    @Test
    fun justATest() {
        val encodedPassword = "\$2a\$16\$l0Uu2SCndZJ7bICPh3B37O6HBtyaMe/T3D1t41WqfJgUruxEpvwFm"
        Assertions.assertTrue(passwordEncoder.matches("1234", encodedPassword))
    }
}