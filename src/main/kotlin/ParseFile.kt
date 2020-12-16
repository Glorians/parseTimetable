import org.apache.poi.ss.usermodel.*
import utils.Checker
import java.io.File
import java.io.FileInputStream


class ParseFile(private var nameFile: String) {

    private var oldFile: Boolean = false // check olfFile
    private lateinit var checker: Checker // object checked
    private lateinit var file: FileInputStream // just file
    private lateinit var wb: Workbook // workbook
    private var countSheet = 0
    private var countColumn = 0
    private lateinit var listGroups: MutableList<String>


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
        var startGroup = 1
        var startSubject = 3

        for (i in 0..countColumn) {
            cursorGroups(sheet, startGroup)
            startGroup += 4
        }
    }

    private fun cursorGroups(sheet: Sheet, startGroup: Int) {
        val lastTableVertical = 242

        //Vertical
        for (height in 0..lastTableVertical) {

            //Horizontal
            cursorSubject(sheet, height, startGroup)
        }
    }

    private fun cursorSubject(sheet: Sheet,height: Int, width: Int) {
        val range = width + 3

        for (x in width..range) {
            val cell = sheet.getRow(height).getCell(x)
            if (cell.cellType == CellType.STRING) {
                println(cell)
            }
            if (cell.cellType == CellType.NUMERIC) {
                println(cell)
            }
        }
    }

    private fun exportCell(cell: Cell?) {
        if (cell?.cellType == CellType.STRING || cell?.cellType == CellType.NUMERIC) {

        }
    }
}

























