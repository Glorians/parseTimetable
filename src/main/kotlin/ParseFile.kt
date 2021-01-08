import model.Faculty
import model.Group
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
    private lateinit var listFaculty: MutableMap<String, MutableMap<String, Group>>
    private lateinit var listNamesSheets: MutableList<String>


    private fun initBaseVariables() {
        checker = Checker()
        oldFile = checker.checkOldFile(nameFile)
        file = FileInputStream(File(nameFile))
        wb = WorkbookFactory.create(file)
        countSheet = wb.numberOfSheets
        listNamesSheets = mutableListOf()
    }

    fun parse() {
        initBaseVariables()

        for (sheet in wb.allNames) {
            val name = sheet.sheetName
            if (name != "" && name.length > 2) {
                listNamesSheets.add(name)
                print("[${sheet.sheetName}] ")
            }
        }

        println("COUNT: ${wb.numberOfSheets}")


        val sheet = wb.getSheetAt(0)
        cursorSheet(sheet)
        file.close()
    }

    private fun cursorSheet(sheet: Sheet): MutableMap<String, Group>{
        listGroups = checker.checkAllGroups(sheet)
        countColumn = checker.checkAllColumnGroups(sheet)
        val mapGroups = mutableMapOf<String, Group>()
        var startPositionGroup = 2 // Start Group

        // Iterator Groups
        for (i in 0 until countColumn) {
            var assembler = Assembler()
            val cursor = Cursor(sheet, assembler)
            cursor.selectGroup(startPositionGroup)
            startPositionGroup += 6
            assembler = cursor.getAssembler()
            val nameGroup = assembler.group.nameGroup
            mapGroups[nameGroup] = assembler.group
        }
        return mapGroups
    }
}

























