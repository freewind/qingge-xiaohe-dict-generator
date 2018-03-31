package example

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class HelloTest {

    @Test
    fun `hello returns greeting words`() {
        assertThat(Hello.hello("you")).isEqualTo("Hello, you!")
    }

}