import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellRangeAddress
import utils.Checker
import utils.MyColor

class Cursor(private val sheet: Sheet) {

    private val countDay = 5
    private var startPositionGroup = 0 // Horizontal
    private var endPositionGroup = 0 // Horizontal
    private var startPositionSubject = 2 // Vertical
    private var endPositionSubject = 7 // Vertical

    private val positionNameGroup = 0 // Horizontal
    private val endTableVertical = 242 // Vertical 5 DAY
    private val sizeGroup = 3 // Horizontal
    private val sizeDay = 8 // Vertical
    private val sizeSubject = 5 // Vertical

    private val checker = Checker()


    private fun scanSubject(): Analyzer {
        val arrayCells = ArrayList<Cell>() // Data subject CELLS
        var startVerticalSubject = startPositionSubject
        val endVerticalSubject = endPositionSubject
        val startHorizontalSubject = startPositionGroup
        val endHorizontalSubject = endPositionGroup

        //Run along the line subject VERTICAL
        for (lineSubject in startVerticalSubject..endVerticalSubject) {

            //Run along the column subject Horizontal
            for (width in startHorizontalSubject..endHorizontalSubject) {

                // Added cells in array
                val cell = sheet.getRow(lineSubject).getCell(width)
                arrayCells.add(cell)
            }

            startVerticalSubject++

            //Stop scan
            if (lineSubject >= endTableVertical) {
                break
            }
        }
        return Analyzer(arrayCells)
    }

    private fun selectSubject(numSubject: Int) {

        val analyzer = scanSubject()

        fun defaultWork() {
            //Run along the line subject VERTICAL
            for (lineSubject in startPositionSubject..endPositionSubject) {

                //Run along the column subject Horizontal
                for (width in startPositionGroup..endPositionGroup) {

                    //Crutch :)
                    if (lineSubject >= endTableVertical) {
                        break
                    }

                    val value = getValueCell(lineSubject, width)
                    if (value != null) {
                        println(value)
                    }
                }

                startPositionSubject++
                if (lineSubject >= endTableVertical) {
                    break
                }
            }
            endPositionSubject = startPositionSubject + sizeSubject // End position next subject

        }

        fun subgroupWork() {
            var startHorizontalSubject = startPositionGroup
            var endHorizontalSubject = endPositionGroup - 2
            var countLineSubject = 0
            val sizeSubgroup = 2
            for (column in 1..2) {
                if (column == 2) {
                    println("|")
                }
                for (lineSubject in startPositionSubject..endPositionSubject) {
                    for (width in startHorizontalSubject..endHorizontalSubject) {
                        val value = getValueCell(lineSubject, width)
                        if (value != null) {
                            println(value)
                        }
                    }
                    countLineSubject++
                }
                startHorizontalSubject += sizeSubgroup
                endHorizontalSubject += sizeSubgroup
            }
            startPositionSubject += countLineSubject / 2
            endPositionSubject = startPositionSubject + sizeSubject
        }

        fun defaultWorkWithPrimaryWeek() {
            var startVerticalSubject = startPositionSubject
            var endVerticalSubject = endPositionSubject-3
            for (primary in 1..2) {
                for (lineSubject in startVerticalSubject..endVerticalSubject) {
                    for (width in startPositionGroup..endPositionGroup) {
                        val value = getValueCell(lineSubject, width)
                        if (value != null) {
                            print("$value ")
                        }
                    }

                    startPositionSubject++
                    if (lineSubject >= endTableVertical) {
                        break
                    }
                }
                if (primary == 1) {
                    println("\n_____________")
                }

                startVerticalSubject = endVerticalSubject+1
                endVerticalSubject += 2
            }
            println("\n")
            endPositionSubject = startPositionSubject + sizeSubject // End position next subject
        }

        fun subgroupWorkWithPrimaryWeek() {
            var startHorizontalSubject = startPositionGroup
            var endHorizontalSubject = endPositionGroup - 2
            val sizeSubgroup = 2
            var countLineSubject = 0
            for (column in 1..2) {
                if (column == 2) {
                    println("|")
                }
                var startVerticalSubject = startPositionSubject
                var endVerticalSubject = endPositionSubject - 3

                for (primary in 1..2) {
                    for (lineSubject in startVerticalSubject..endVerticalSubject) {
                        for (width in startHorizontalSubject..endHorizontalSubject) {
                            val value = getValueCell(lineSubject, width)
                            if (value != null) {
                                println(value)
                            }
                        }
                        countLineSubject++
                    }
                    if (primary == 1) {
                        println("\n_____________")
                    }

                    startVerticalSubject = endVerticalSubject + 1
                    endVerticalSubject += 3
                }

                startHorizontalSubject += sizeSubgroup
                endHorizontalSubject += sizeSubgroup
            }

            startPositionSubject += countLineSubject / 2
            endPositionSubject = startPositionSubject + sizeSubject
        }

        // If there is a subgroups & primary week
        if (analyzer.checkSubgroupsWithPrimaryWeek()) {
            subgroupWorkWithPrimaryWeek()
        }

        // If there is a subgroups
        else if (analyzer.checkSubgroups()) {
            subgroupWork()
        }

        // If there is a primary week
        else if (analyzer.checkPrimaryWeek()) {
            defaultWorkWithPrimaryWeek()
        }

        else {
            defaultWork()
        }


    }

    private fun selectDay(numDay: Int) {
        for (numSubject in 1..sizeDay) {
            println("${MyColor.ANSI_PURPLE}Subject #$numSubject ${MyColor.ANSI_RESET}")
            selectSubject(numSubject)
        }
    }

    fun selectGroup(startPositionGroup: Int) {
        this.startPositionGroup = startPositionGroup
        endPositionGroup = startPositionGroup + sizeGroup
        println(MyColor.ANSI_GREEN + getNameGroup(startPositionGroup) + MyColor.ANSI_RESET)
        for (numDay in 1.. countDay) {
            println("${MyColor.ANSI_BLUE}Day #$numDay ${MyColor.ANSI_RESET}")
            selectDay(numDay)
        }
    }

    private fun getNameGroup(startPositionGroup: Int): String? {
        val name = getValueCell(positionNameGroup, startPositionGroup-2)
        return if (name is String) {
            name
        } else null
    }

    private fun getValueCell(heightPosition: Int, widthPosition: Int): Any? {
        val cell = sheet.getRow(heightPosition).getCell(widthPosition)
        if (cell.cellType == CellType.STRING) {
            return cell.stringCellValue
        }
        if (cell.cellType == CellType.NUMERIC) {
            return cell.numericCellValue
        }

        return null
    }

    private fun getValueCell(mergedCells: CellRangeAddress): Any? {
        val cell = sheet.getRow(mergedCells.firstRow).getCell(mergedCells.firstColumn)

        if (cell.cellType == CellType.STRING) {
            return cell.stringCellValue
        }
        if (cell.cellType == CellType.NUMERIC) {
            return cell.numericCellValue
        }
        return null
    }


}