package org.yagoff.gga.gods

import java.util.concurrent.atomic.AtomicLong

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.typesafe.scalalogging.LazyLogging

case class IdGenerator() {
  private val id = new AtomicLong
  def next: Long = id.incrementAndGet
}

class GodsController extends LazyLogging {
  //database
  var db = mutable.HashSet[God]()

  private val idGenerator = IdGenerator()
  def nextTaskId(): Long = idGenerator.next

  def addGod(god: God): God = {
    logger.debug(s"God before adding id: $god")
    val godWithId = god.copy(id = Some(nextTaskId()))
    db += godWithId
    godWithId
  }

  def getDB() = db

  def getGodById(id: Long): Future[God] = Future {
    db.filter(_.id.get == id).head
  }

  def update(id: Long, updatedGod: God): Future[God] = Future {
    val currentGod = db.filter(_.id.get == id).head
    db -= currentGod
    db += updatedGod
    updatedGod
  }

  def delete(id: Long) = {
    val god = db.filter(_.id.get == id).head
    db -= god
  }
}