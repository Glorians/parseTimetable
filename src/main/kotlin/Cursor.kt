import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellRangeAddress
import utils.Checker
import utils.MyColor

class Cursor(private val sheet: Sheet) {

    private val countDay = 5
    private var startPositionGroup = 0 // Horizontal
    private var endPositionGroup = 0 // Horizontal
    private var startPositionSubject = 2 // Vertical
    private var endPositionSubject = 6 // Vertical

    private val positionNameGroup = 0 // Horizontal
    private val endTableVertical = 242 // Vertical
    private val sizeGroup = 4 // Horizontal
    private val sizeDay = 8 // Vertical
    private val sizeSubject = 7 // Vertical

    private val checker = Checker()




    private fun selectSubject(numSubject: Int) {
        for (lineSubject in startPositionSubject..endPositionSubject) {
            for (width in startPositionGroup..endPositionGroup) {
                if (lineSubject >= endTableVertical) {
                    break
                }
                val value = getValueCell(lineSubject, width)
                if (value != "" && value != " ") {
                    println("CELL($lineSubject:$width) -> $value")
                }
            }
            startPositionSubject++
            if (lineSubject >= endTableVertical) {
                break
            }
        }
        endPositionSubject = startPositionSubject + sizeSubject
    }

    private fun selectDay(numDay: Int) {
        for (numSubject in 1..sizeDay) {
            selectSubject(numSubject)
        }
    }

    fun selectGroup(startPositionGroup: Int) {
        this.startPositionGroup = startPositionGroup
        endPositionGroup = startPositionGroup + sizeGroup
        println(MyColor.ANSI_GREEN + getNameGroup(startPositionGroup) + MyColor.ANSI_RESET)
        for (numDay in 1.. countDay) {
            selectDay(numDay)
        }

    }

    private fun getNameGroup(startPositionGroup: Int): String? {
        val name = getValueCell(positionNameGroup, startPositionGroup-1)
        return if (name is String) {
            name
        } else null
    }

    private fun getValueCell(heightPosition: Int, widthPosition: Int): Any? {
        val cell = sheet.getRow(heightPosition).getCell(widthPosition)
        if (cell.cellType == CellType.STRING) {
            return cell.stringCellValue
        }
        if (cell.cellType == CellType.NUMERIC) {
            return cell.numericCellValue
        }

        if (cell.cellType == CellType.BLANK || cell.cellType == CellType._NONE) {
            return ""
        }

        return null
    }

    private fun getValueCell(mergedCells: CellRangeAddress): Any? {
        val cell = sheet.getRow(mergedCells.firstRow).getCell(mergedCells.firstColumn)

        if (cell.cellType == CellType.STRING) {
            return cell.stringCellValue
        }
        if (cell.cellType == CellType.NUMERIC) {
            return cell.numericCellValue
        }
        return null
    }


}