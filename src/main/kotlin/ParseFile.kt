import org.apache.poi.ss.usermodel.*
import utils.Checker
import java.io.File
import java.io.FileInputStream


class ParseFile(private var nameFile: String) {

    private var OLD_FILE: Boolean = false
    private lateinit var checker: Checker
    private lateinit var file: FileInputStream
    private lateinit var wb: Workbook
    private var countSheet = 0


    private fun initBaseVariables() {
        checker = Checker()
        OLD_FILE = checker.checkOldFile(nameFile)
        file = FileInputStream(File(nameFile))
        wb = WorkbookFactory.create(file)
        countSheet = wb.numberOfSheets
    }


    fun parse() {
        initBaseVariables()

        if (OLD_FILE) {
            parseOldFile()
        } else {
            parseNewFile()
        }

        val sheet = wb.getSheetAt(0)
        cursorSheet(sheet)
        file.close()
    }

    private fun parseOldFile() {}

    private fun parseNewFile() {}

    private fun cursorSheet(sheet: Sheet) {
        val listGroups = checker.checkAllGroups(sheet)
        var nextGroup = 0

        for (i in 0..listGroups.size) {
            cursorGroups(sheet, nextGroup)
            nextGroup += 5
        }
    }

    private fun cursorGroups(sheet: Sheet, nextGroup: Int) {
        val last = 242
        for (i in 0..last) {
            for (x in nextGroup..nextGroup + 5) {
                val cell = sheet.getRow(i).getCell(x)
                exportCell(cell)
            }
        }
    }

    private fun exportCell(cell: Cell) {
        if (cell.cellType == CellType.STRING || cell.cellType == CellType.NUMERIC) {
            when (checker.checkColor(cell)) {
                "blue" -> blue()
                "aqua" -> aqua()
                "" -> etc()
            }
        }
    }
}

























