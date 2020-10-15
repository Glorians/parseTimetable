package utils

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Sheet


class Checker {

    fun checkOldFile(nameFile: String): Boolean {
        val arrayChars = nameFile.toCharArray()
        val lastChar = arrayChars[arrayChars.size - 1]
        if (lastChar != 'x') {
            return true
        }
        return false
    }

    fun checkColor(cell: Cell): String {
        val cellColor = cell.cellStyle.fillForegroundColor
        val colors = arrayListOf(
                IndexedColors.BLUE.index, IndexedColors.TURQUOISE.index
        )
        if (cell.cellType == CellType.STRING) {
            when (cellColor) {
                colors[0] -> {
                    println("${MyColor().ANSI_BLUE}${cell.stringCellValue}")
                    print(MyColor().ANSI_RESET)
                    return "blue"
                }
                colors[1] -> {
                    println("${MyColor().ANSI_CYAN}${cell.stringCellValue}")
                    print(MyColor().ANSI_RESET)
                    return "aqua"
                }
                else -> {

                    if (cell.stringCellValue != "" || cell.stringCellValue != " ") {
                        println("${cell.stringCellValue} ${MyColor().ANSI_RESET}")
                    }
                    return ""
                }
            }
        }
        return ""
    }

    fun checkAllGroups(sheet: Sheet): MutableList<String> {
        val listGroups = mutableListOf<String>()
        for (i in 0..1000) {
            try {
                val cell = sheet.getRow(0).getCell(i)
                if (cell.stringCellValue != "") {
                    listGroups.add(cell.stringCellValue)
                }
            }
            catch (e: Exception) {
                break
            }
        }
        return listGroups
    }
    fun checkGroup(cell: Cell, listGroup: ArrayList<String>): Boolean{
        val group = cell.stringCellValue
        for (i in 0..listGroup.size) {
            return group == listGroup[i]
        }
        return false
    }
}