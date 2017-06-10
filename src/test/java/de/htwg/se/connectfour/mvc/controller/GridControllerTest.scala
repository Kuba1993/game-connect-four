package de.htwg.se.connectfour.mvc.controller

import de.htwg.se.connectfour.mvc.model.{Cell, CellType, Grid}
import org.specs2.mutable.Specification

class GridControllerTest extends Specification {

  "New GridController of grid 3x2" should {

    val grid = new Grid(3, 2)

    val xCell = Cell(2, 0, CellType.SECOND)
    val oCell = Cell(0, 1, CellType.FIRST)
    grid.setupCell(xCell)
    grid.setupCell(oCell)

    val gridController = new GridController(grid)

    "have valid and invalid cells" in {
      gridController.isCellValid(grid.MAX_COLUMN + 1, grid.MAX_ROW + 1) must be_==(false)
      gridController.isCellValid(0, 0) must be_==(true)
    }

    "have valid nonempty cells" in {
      gridController.isCellValidAndNotEmpty(oCell.x, oCell.y) must be_==(true)
      gridController.isCellValidAndNotEmpty(xCell.x, xCell.y) must be_==(true)
      gridController.isCellValidAndNotEmpty(1, 1) must be_==(false)
      gridController.isCellValidAndNotEmpty(grid.MAX_COLUMN + 1, 0) must be_==(false)
      gridController.isCellValidAndNotEmpty(0, grid.MAX_ROW + 1) must be_==(false)
    }

    "have empty grid" in {
      val localGridController = new GridController(grid)
      localGridController.gameStatus = StatusType.SET
      localGridController.createEmptyGrid(3, 2)
      localGridController.gameStatus must be_==(StatusType.NEW)
    }

    "have undone move" in{
      val localGridController = new GridController(grid)
      localGridController.undo()
      localGridController.gameStatus must be_==(StatusType.UNDO)
    }

    "have redone move" in{
      val localGridController = new GridController(grid)
      localGridController.redo()
      localGridController.gameStatus must be_==(StatusType.REDO)
    }

    "return rowSize 2" in{
      gridController.rowSize must be_==(2)
    }

    "return columnSize 3" in{
      gridController.columnSize must be_==(3)
    }

    "have full grid" in {
      val localGrid = new Grid
      for (row <- 0 until localGrid.rows; column <- 0 until localGrid.columns) {
        localGrid.set(column, row, CellType.SECOND)
      }
      val localGridController = new GridController(localGrid)
      localGridController.isFull must be_==(true)
    }

    "have statusText" in {
      gridController.statusText must be_==("A new game was created")
    }

    "have added cell" in {
      val localGridController = new GridController(grid)
      localGridController.addCell(0, CellType.FIRST)
      localGridController.gameStatus must be_==(StatusType.SET)
    }

    "have valid and not full column" in {
      gridController.isColumnValidAndNotFull(0) must be_==(true)
    }

    "have removed symbol from column" in {
      val localGridController = new GridController(grid)
      localGridController.set(0,0, CellType.FIRST)
      localGridController.removeSymbolFromColumn(0)
      localGridController.grid.cell(0,0).cellType must be_==(CellType.EMPTY)
    }

    "have won with this move" in {
      val localGrid = new Grid(7,6)
      val localGridController = new GridController(localGrid)
      for (row <- 0 until 4){
        localGridController.set(row,5, CellType.FIRST)
      }
      //print(localGridController.grid.toString)

      localGridController.isMoveWinning(3) must be_==(true)

    }

  }
}
