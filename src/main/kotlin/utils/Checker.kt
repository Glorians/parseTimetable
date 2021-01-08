package utils

import org.apache.poi.ss.usermodel.*


open class Checker {

    fun checkOldFile(nameFile: String): Boolean {
        val arrayChars = nameFile.toCharArray()
        val lastChar = arrayChars[arrayChars.size - 1]
        if (lastChar != 'x') {
            return true
        }
        return false
    }

    fun checkSubgroup(cell: Cell): Boolean {
        if (cell.cellType == CellType.STRING) {
            val value = cell.stringCellValue
            if (value.length == 1 && checkColor(cell) == "aqua") {
                return true
            }
        }
        return false
    }

    fun checkColor(cell: Cell): String {
        val cellColor = cell.cellStyle.fillForegroundColor
        val colors = arrayListOf(
                IndexedColors.BLUE.index, IndexedColors.TURQUOISE.index,
                IndexedColors.GREY_25_PERCENT.index
        )


        if (cell.cellType == CellType.STRING || cell.cellType == CellType.NUMERIC) {
            when (cellColor) {
                colors[0] -> {
                    return "blue"
                }
                colors[1] -> {
                    return "aqua"
                }
                colors[2] -> {
                    return "grey"
                }
            }
        }
        return ""
    }

    fun checkAllColumnGroups(sheet: Sheet): Int {
        val listGroups = mutableListOf<String>()
        for (i in 0..1000) {
            try {
                val cell = sheet.getRow(0).getCell(i)
                val str = cell.stringCellValue
                if (str != "") {
                    listGroups.add(str)
                }
            }
            catch (e: Exception) {
                break
            }
        }
        return listGroups.size
    }

    fun checkAllGroups(sheet: Sheet): MutableList<String> {
        val listGroups = mutableListOf<String>()

        // We go through all the top cells in search of group names
        for (i in 0..1000) {
            try {
                val cell = sheet.getRow(0).getCell(i)
                val str = cell.stringCellValue
                if (str != "" && !checkDoubleGroup(str)) {
                    listGroups.add(str)
                }
                // If more than one group
                else {
                    listGroups.addAll(fixDoubleGroup(str))
                }
            }
            catch (e: Exception) {
                break
            }
        }
        return listGroups
    }

    fun checkGroup(cell: Cell, listGroup: MutableList<String>): Boolean{
        val group = cell.stringCellValue
        for (i in 0..listGroup.size) {
            return group == listGroup[i]
        }
        return false
    }


    private fun fixDoubleGroup(str: String): List<String> {
        if (checkDoubleGroup(str)) {
            return str.split(" ")
        }
        return emptyList()
    }

    fun checkDoubleGroup(str: String): Boolean {
        val arrayGroup = str.split(" ")
        return arrayGroup.size == 2
    }

    fun checkBottomBorder(cell: Cell): Boolean {
        return cell.cellStyle.borderBottom == BorderStyle.MEDIUM_DASHED
    }

    fun checkVoidSubjectName(str: String):Boolean {
        if (str == "-") {
            return true
        }
        return false
    }

}