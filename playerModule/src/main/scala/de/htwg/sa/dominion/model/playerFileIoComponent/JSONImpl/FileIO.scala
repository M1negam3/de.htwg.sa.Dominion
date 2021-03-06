package de.htwg.sa.dominion.model.playerFileIoComponent.JSONImpl

import java.io.{File, PrintWriter}

import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.playerFileIoComponent.IPlayerFileIO
import de.htwg.sa.dominion.model.playerComponent.IPlayer
import play.api.libs.json.Json

import scala.io.Source
import scala.util.Try

class FileIO extends IPlayerFileIO {
  override def load(player: IPlayer, path: String): Try[IPlayer] = {
    Try {
      val source = Source.fromFile(path + ".json")
      val string = source.getLines().mkString
      source.close
      val json = Json.parse(string)
      player.fromJson(json)
    }
  }

  override def save(player: IPlayer, path: String): Try[Boolean] = {
    Try {
      val printWriter = new PrintWriter(new File(path + ".json"))
      printWriter.write(Json.prettyPrint(player.toJson))
      printWriter.close()
      true
    }
  }
}
