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

        val sheet = wb.getSheetAt(0)
        cursorSheet(sheet)
        file.close()
    }

    private fun cursorSheet(sheet: Sheet) {
        listGroups = checker.checkAllGroups(sheet)
        countColumn = checker.checkAllColumnGroups(sheet)

        var startPositionGroup = 1 // Start Group

        // Iterator Groups
        for (i in 0 until countColumn) {
            val cursor = Cursor(sheet)
            cursor.selectGroup(startPositionGroup)
            startPositionGroup += 6
        }
    }
}

























