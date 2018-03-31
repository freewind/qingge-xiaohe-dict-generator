package example

object Hello {
    @JvmStatic
    fun main(args: Array<String>) {
        println(hello("Kotlin"))
    }

    fun hello(name: String): String = "Hello, $name!"

}

