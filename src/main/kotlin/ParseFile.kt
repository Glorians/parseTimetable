
import org.apache.poi.ss.usermodel.*
import java.io.File
import java.io.FileInputStream
import kotlin.collections.ArrayList


class ParseFile {

    private val file = FileInputStream(File("timetable.xls"))
    private lateinit var  workbook: Workbook
    private var countSheet = 0
    private var sheets = ArrayList<Sheet>()
    private var result = ""

    fun parse() {
        workbook = WorkbookFactory.create(file) // Файл
        countSheet = workbook.numberOfSheets // Количество страниц

        // В массив добавлем страницы
        for (sheet in workbook) {
            sheets.add(sheet)
        }

        val sheet = workbook.getSheetAt(0)
        for (i in 0..242) {
            for (x in 0..5) {
                val cell = sheet.getRow(i).getCell(x)
                if(cell.cellType == CellType.STRING) {
                    println(cell.stringCellValue)
                }
                if (cell.cellType == CellType.NUMERIC) {
                    println(cell.numericCellValue.toString())
                }

            }

        }



    }
}




























