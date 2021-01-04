import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import utils.Checker

class Analyzer(private val arrayCells: ArrayList<Cell>) {

    private val checker = Checker()
    var checkPrimaryWeekStatus = false

    init {
        checkPrimaryWeek()
    }

    fun checkSubGroups(): Boolean {
        val cellOne = arrayCells[0]
        if (cellOne.cellType == CellType.STRING) {
            if (checker.checkSubgroup(cellOne)) {
                return true
            }
        }
        return false
    }

    private fun checkPrimaryWeek() {
        for (cell in arrayCells) {
            checkPrimaryWeekStatus = checker.checkBottomBorder(cell)
        }
    }

    private fun getValue(cell: Cell): Any? {
        if (cell.cellType == CellType.STRING) {
            return cell.stringCellValue
        }
        if (cell.cellType == CellType.NUMERIC) {
            return cell.numericCellValue
        }
        return null
    }
}