package utils

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.IndexedColors
import java.awt.Color


class Checker {

    fun checkOldFile(nameFile: String): Boolean {
        val arrayChars = nameFile.toCharArray()
        val lastChar = arrayChars[arrayChars.size - 1]
        if (lastChar != 'x') {
            return true
        }
        return false
    }

    fun checkColor(cell: Cell) {
        val cellColor = cell.cellStyle.fillForegroundColor
        val colors = arrayListOf(
                IndexedColors.BLUE.index, IndexedColors.TURQUOISE.index
        )
        if (cell.cellType == CellType.STRING) {
            when (cellColor) {
                colors[0] -> {
                    println("${MyColor().ANSI_BLUE}${cell.stringCellValue}")
                    print(MyColor().ANSI_RESET)
                }
                colors[1] -> {
                    println("${MyColor().ANSI_CYAN}${cell.stringCellValue}")
                    print(MyColor().ANSI_RESET)
                }
                else -> {
                    if (cell.stringCellValue != "" || cell.stringCellValue != " ") {
                        println("${cell.stringCellValue} ${MyColor().ANSI_RESET}")
                    }
                }
            }
        }
    }
}