package de.htwg.sa.dominion.model.playercomponent

import de.htwg.sa.dominion.model.PlayerInterface
import de.htwg.sa.dominion.model.cardcomponent.{Card, Deck}

import scala.collection.mutable.ListBuffer
import scala.util.Random

case class Player(name: String, value: Int, deck: List[Card], stacker: List[Card], handCards: List[Card],
                  actions: Int, buys: Int, money: Int, victoryPoint: Int) extends PlayerInterface {

  override def constructPlayerNameString(): String = {
    this.name
  }

  override def constructPlayerDeckString(): String = {
    val deckStringList: List[String] = for ((card, idx) <- this.deck.zipWithIndex) yield card.cardName + " (" + idx + ")"
    val playerDeckString: String = deckStringList.mkString("\n")
    playerDeckString.toString

  }

  override def constructPlayerStackerString(): String = {
    val stackerStringList: List[String] = for ((card, idx) <- this.stacker.zipWithIndex) yield card.cardName + " (" + idx + ")"
    val playerStackerString: String = stackerStringList.mkString("\n")
    playerStackerString.toString

  }

  override def constructPlayerHandString(): String = {
    val handStringList: List[String] = for ((card, idx) <- this.handCards.zipWithIndex) yield card.cardName + " (" + idx + ")"
    val playherHandString: String = handStringList.mkString("\n")
    playherHandString.toString
  }

  override def updateActions(updatedActionValue: Int): Player = {
    this.copy(actions = updatedActionValue)
  }

  override def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player = {
    if (cardsToDraw == 0) {
      return playerToUpdate
    }
    if (playerToUpdate.deck.isEmpty) {
      val updatedDeck = shuffle(playerToUpdate.stacker)
      val updatedStacker = List()
      val updatedHand = List.concat(playerToUpdate.handCards, List(updatedDeck.head))
      val finalDeck = updatedDeck.drop(1)
      val updatedPlayer: Player = playerToUpdate.copy(deck = finalDeck, handCards = updatedHand, stacker = updatedStacker)
      updateHand(cardsToDraw - 1, updatedPlayer)
    } else {
      val updatedHand = List.concat(playerToUpdate.handCards, List(playerToUpdate.deck.head))
      val finalDeck = playerToUpdate.deck.drop(1)
      val updatedPlayer = playerToUpdate.copy(deck = finalDeck, handCards = updatedHand)
      updateHand(cardsToDraw - 1, updatedPlayer)
    }
  }

  private def shuffle(cardListToShuffle: List[Card]): List[Card] = {
    val random = new Random
    val shuffledList: List[Card] = random.shuffle(cardListToShuffle)
    shuffledList
  }
}
