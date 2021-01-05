import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import utils.Checker

class Analyzer(private val arrayCells: ArrayList<Cell>) {

    private val checker = Checker()
    var checkPrimaryWeekStatus = false

    init {
        checkPrimaryWeek()
    }

    fun checkSubgroups(): Boolean {
        val cellOne = arrayCells[0]
        if (cellOne.cellType == CellType.STRING) {
            if (checker.checkSubgroup(cellOne)) {
                return true
            }
        }
        return false
    }

    fun checkPrimaryWeek(): Boolean {
        for (cell in arrayCells) {
            if (checker.checkBottomBorder(cell)) {
                return true
            }
        }
        return false
    }

    fun checkSubgroupsWithPrimaryWeek(): Boolean {
        return checkSubgroups() && checkPrimaryWeek()
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