package org.yagoff.gga.gods

import akka.actor.ActorSystem

import scala.concurrent.{ExecutionContext, Future}
import com.typesafe.scalalogging.LazyLogging


class GodsController extends LazyLogging {

  implicit val system = ActorSystem("ctrl-system")
  implicit lazy val ec = system.dispatcher

  lazy val godsDao = new GodsDao()(ec)

  def addGod(god: God): Future[God] = {
    logger.debug(s"God before adding id: $god")
    val godCreated = godsDao.create(god)
    godCreated
  }

//  def getDB() = db

//  def getGodById(id: Long): Future[God] = Future {
//    db.filter(_.id.get == id).head
//  }
//
//  def update(id: Long, updatedGod: God): Future[God] = Future {
//    val currentGod = db.filter(_.id.get == id).head
//    db -= currentGod
//    db += updatedGod
//    updatedGod
//  }
//
//  def delete(id: Long) = {
//    val god = db.filter(_.id.get == id).head
//    db -= god
//  }
}