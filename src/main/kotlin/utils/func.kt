package utils

fun printd(str: String) {
    val color = MyColor()
    println(color.ANSI_RED + str + color.ANSI_RESET)
}
