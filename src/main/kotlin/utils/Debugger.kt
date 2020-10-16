package utils

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType

class Debugger {
    private val colorConsole = MyColor()
    private val checker = Checker()
    private var countPos = 0

    fun printConsoleAll(cell: Cell?) {
        val color = cell?.let { checker.checkColor(it) }
        if (cell?.cellType == CellType.STRING) {
            val str = cell.stringCellValue

            when (color) {
                "blue" -> if (str.length > 1) {println(
                        colorConsole.ANSI_BLUE
                                + str +
                                colorConsole.ANSI_RESET)}

                "aqua" -> println(
                        colorConsole.ANSI_CYAN
                                + str +
                                colorConsole.ANSI_RESET)
                "" -> {
                    if (countPos != 2) {
                        print("$str ")
                        countPos++
                    }
                    else {println("$str ")
                        countPos = 0}

                }
            }

        }
        if (cell?.cellType == CellType.NUMERIC) {
            val num = cell.numericCellValue.toInt()
            when (checker.checkColor(cell)) {
                "grey" -> {
                    println(
                            colorConsole.ANSI_YELLOW
                                    + num +
                                    colorConsole.ANSI_RESET

                    )
                }
                else -> {
                    print(
                            colorConsole.ANSI_PURPLE
                                    + num +
                                    colorConsole.ANSI_RESET + " "
                    )
                    if (checker.checkBottomBorder(cell)) {
                        printd("BINGOO")
                    }

                }
            }



        }
    }
}