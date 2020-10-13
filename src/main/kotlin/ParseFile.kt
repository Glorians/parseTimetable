
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
    private var listFreshInputData = arrayListOf<Any>()


    private fun initBaseVariables() {
        checker = Checker()
        OLD_FILE = checker.checkOldFile(nameFile)
        file = FileInputStream(File(nameFile))
        wb = WorkbookFactory.create(file)
        countSheet = wb.numberOfSheets
    }


    fun parse() {
        initBaseVariables()

        if (OLD_FILE){
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

    private fun exportCell(cell: Cell) {
        if (cell.cellType == CellType.STRING) {
            println(cell.stringCellValue)
        }
        if (cell.cellType == CellType.NUMERIC) {
            println(cell.numericCellValue)
        }
    }

    private fun cursorSheet(sheet: Sheet) {
        val last = 242
        for (i in 0..last) {
            for (x in 0..5) {
                try {
                    val cell = sheet.getRow(i).getCell(x)
                    checker.checkColor(cell)
                }
                catch (e: Exception) {
                    System.err.println("Error")
                }
//                exportCell(cell)
            }
        }
    }

}




























