package org.yagoff.gga.gods

import java.util.UUID

import com.typesafe.config.ConfigFactory
import org.yagoff.gga.db.{DatabaseConfig, SqlDatabase}

import scala.concurrent.{ExecutionContext, Future}

class GodsDao()(implicit val ec: ExecutionContext) extends GodsSchema {

  lazy val config = new DatabaseConfig {
    override def rootConfig = ConfigFactory.load()
  }

  lazy val sqlDatabase = SqlDatabase.create(config)

  sqlDatabase.updateSchema()

  import sqlDatabase._
  import sqlDatabase.driver.api._


  val insertQuery = gods returning gods.map(_.id) into ((g, id) => g.copy(id = id))
  def create(god: God) : Future[God] = {
    val godWithId = god.copy(id = Some(UUID.randomUUID))
    val action = insertQuery += godWithId
    db.run(action)
  }

}

/**
  * The schemas are in separate traits, so that if your DAO would require to access (e.g. join) multiple tables,
  * you can just mix in the necessary traits and have the `TableQuery` definitions available.
  */
trait GodsSchema {

  protected val sqlDatabase: SqlDatabase

  import sqlDatabase.driver.api._

  protected val gods = TableQuery[Gods]

  protected class Gods(tag: Tag) extends Table[God](tag, "gods") {
    def id        = column[Option[UUID]]("id", O.PrimaryKey)
    def name      = column[String]("name")
    def purpose   = column[String]("purpose")

    def * = (id, name, purpose) <> ((God.apply _).tupled, God.unapply)
  }

}