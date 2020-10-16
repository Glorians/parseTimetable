import org.apache.poi.ss.usermodel.*
import utils.Checker
import utils.Debugger
import utils.MyColor
import java.io.File
import java.io.FileInputStream


class ParseFile(private var nameFile: String) {

    private var oldFile: Boolean = false
    private lateinit var checker: Checker
    private lateinit var file: FileInputStream
    private lateinit var wb: Workbook
    private var countSheet = 0
    private var countColumn = 0
    private val debugger = Debugger()
    private lateinit var listGroups: MutableList<String>
    private val distributor = Distributor()


    private fun initBaseVariables() {
        checker = Checker()
        oldFile = checker.checkOldFile(nameFile)
        file = FileInputStream(File(nameFile))
        wb = WorkbookFactory.create(file)
        countSheet = wb.numberOfSheets
    }


    fun parse() {
        initBaseVariables()

        if (oldFile) {
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
        listGroups = checker.checkAllGroups(sheet)
        countColumn = checker.checkAllColumnGroups(sheet)
        var nextGroup = 0

        for (i in 0..countColumn) {
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
                debugger.printConsoleAll(cell)
            }
        }
    }

    private fun exportCell(cell: Cell?) {
        if (cell?.cellType == CellType.STRING || cell?.cellType == CellType.NUMERIC) {
            distributor.freshData(cell, listGroups)
        }
    }
}

























