import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import utils.Checker

class Distributor {

    private lateinit var cell: Cell
    private lateinit var listGroups: MutableList<String>
    private lateinit var assemblerGroup: AssemblerGroups
    private val checker = Checker()
    private var positionSubject = 0

    fun freshData(cell: Cell, listGroups: MutableList<String>) {
        this.cell = cell
        this.listGroups = listGroups

        if (cell.cellType == CellType.STRING) {
            val str = cell.stringCellValue
            when (checker.checkColor(cell)) {
                "blue" -> {
                    processBlue(str)
                }
                "aqua" -> {
                    processAqua(str)
                }

                "" -> {
                    processDefault(str)
                }
            }
        }

        if (cell.cellType == CellType.NUMERIC) {
            if (checker.checkColor(cell) == "grey") {
                processGrey(cell.numericCellValue.toInt())
            }
        }
    }

    private fun processBlue(str: String) {
        if (checker.checkGroup(cell, listGroups)) {
            assemblerGroup = AssemblerGroups()
        }
    }

    fun processAqua(str: String) {
        if (str.length < 2) {

        }
    }

    fun processGrey(num: Int) {

    }

    fun processDefault(str: String) {

    }

    fun processNum() {

    }
}
