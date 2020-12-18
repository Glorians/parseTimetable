import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet

class Cursor(private val sheet: Sheet) {

    private val countDay = 5
    private val startPositionSubject = 3 // Vertical
    private val positionNameGroup = 0 // Horizontal
    private val endTableVertical = 242 // Vertical
    private val sizeGroup = 5 // Horizontal
    private val sizeDay = 8 // Vertical
    private val sizeSubject = 6 // Vertical



    private fun selectSubject() {
        for (x in 1..sizeSubject) {

        }
    }

    private fun selectDay() {
        for (x in 1..sizeDay) {
            selectSubject()
        }
    }

    fun selectGroup(startPositionGroup: Int) {
        println(getNameGroup(startPositionGroup))
        for (x in 1.. countDay) {
            selectDay()
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
        return null
    }

}