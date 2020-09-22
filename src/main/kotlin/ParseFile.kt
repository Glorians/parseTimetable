
import model.Group
import org.apache.commons.codec.binary.Hex
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFColor
import java.io.File
import java.io.FileInputStream
import kotlin.collections.ArrayList


class ParseFile {

    private val file = FileInputStream(File("timetable.xls"))
    private lateinit var  workbook: Workbook
    private var countSheet = 0
    private var sheets = ArrayList<Sheet>()
    private var listFreshInputData = arrayListOf<Any>()
    private var mapColors = mutableMapOf<String, XSSFColor>()

    private fun initColor(wb: Workbook) {
        val codeColorBaseCSBC = "0000FF"
        val codeColorSelectSubgroup = "00FFFF"

        fun splitCodeColor (codeColor: String): CharArray {
            return codeColor.toCharArray()
        }

        val byteRBGBase = Hex.decodeHex(splitCodeColor(codeColorBaseCSBC))
        val byteRGBSelGroup = Hex.decodeHex(splitCodeColor(codeColorSelectSubgroup))

        val colorBaseCSBC = XSSFColor(byteRBGBase, null)
        val colorSelGroup = XSSFColor(byteRGBSelGroup, null)
        mapColors["baseCSBC"] = colorBaseCSBC
        mapColors["subgroup"] = colorSelGroup
    }


    fun parse() {
        workbook = WorkbookFactory.create(file) // Файл
        countSheet = workbook.numberOfSheets // Количество страниц
        initColor(workbook)

        // В массив добавлем страницы
        for (sheet in workbook) {
            sheets.add(sheet)
        }

        val sheet = workbook.getSheetAt(0)
        for (i in 0..242) {
            for (x in 0..5) {
                val cell = sheet.getRow(i).getCell(x)
                if(cell.cellType == CellType.STRING) {
                    if (cell.cellStyle.fillBackgroundColor == mapColors["baseCSBC"])
                    listFreshInputData.add(cell.stringCellValue)
                }
                if (cell.cellType == CellType.NUMERIC) {
                    listFreshInputData.add(cell.numericCellValue)
                }

            }
        }

        for(item in listFreshInputData) {
            print("$item ")
        }

        val group = Group(listFreshInputData[0].toString())



    }
}




























