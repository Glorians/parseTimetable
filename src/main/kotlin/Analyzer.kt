import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import utils.Checker

class Analyzer(private val arrayCells: ArrayList<Cell>) {

    private val checker = Checker()

    fun checkSubgroups(): Boolean {
        val cellOne = arrayCells[0]
        if (cellOne.cellType == CellType.STRING) {
            if (checker.checkSubgroup(cellOne)) {
                return true
            }
        }
        return false
    }

    fun checkParityWeek(): Boolean {
        for (cell in arrayCells) {
            if (checker.checkBottomBorder(cell)) {
                return true
            }
        }
        return false
    }

    fun checkSubgroupsWithParityWeek(): Boolean {
        return checkSubgroups() && checkParityWeek()
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