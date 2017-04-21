package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{GamingPlayers, Grid}

class PrintGame(grid: Grid, gamePlayers: GamingPlayers) {

  def welcomePlayers(): Unit = {
    println("-- Welcome to the game --")
  }

  def displayGridWithMessage(): Unit = {
    displayGrid()
    displayGoingMessage()
  }

  def displayGrid(): Unit = {
    println(grid.niceString())
  }

  private def displayGoingMessage(): Unit = {
    val number = if (gamePlayers.isFirstGoing) "First" else "Second"
    println(number + " player is going.")
  }

  def congratulateWinner(): Unit = {
    displayGrid()
    println("Congratulations!")
    println(gamePlayers.currentPlayer.name + ", you won.")
  }

  def wrongMove(columnMove: Int): Unit = {
    println("Column " + columnMove + " is invalid. Please try again.")
  }
}
