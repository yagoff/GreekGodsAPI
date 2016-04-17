package org.yagoff.gga.db

import java.time.{OffsetDateTime, ZoneOffset}

import com.typesafe.scalalogging.LazyLogging
import org.flywaydb.core.Flyway
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend.Database

case class SqlDatabase(db: Database, driver: JdbcProfile, connectionString: JdbcConnectionString) extends LazyLogging{

  import driver.api._

  implicit val offsetDateTimeColumnType = MappedColumnType.base[OffsetDateTime, java.sql.Timestamp](
    dt => new java.sql.Timestamp(dt.toInstant.toEpochMilli),
    t => t.toInstant.atOffset(ZoneOffset.UTC)
  )

  def updateSchema() {
    val flyway = new Flyway()
    logger.debug(s"Connection URL: ${connectionString.url}. User: ${connectionString.username}.")
    flyway.setDataSource(connectionString.url, connectionString.username, connectionString.password)
    flyway.migrate()
  }

  def close() {
    db.close()
  }

}

case class JdbcConnectionString(url: String, username: String = "", password: String = "")

object SqlDatabase extends LazyLogging {

  def embeddedConnectionStringFromConfig(config: DatabaseConfig): String = {
    val url = config.H2Url
    val fullPath = url.split(":")(3)
    logger.info(s"Using an embedded database, with data files located at: $fullPath")
    url
  }

  def create(config: DatabaseConfig): SqlDatabase = {
    if (config.PostgresServerName.length > 0)
      createPostgres(config)
    else
      createEmbedded(config)
  }

  def postgresUrl(host: String, port: String, dbName: String) =
    s"jdbc:postgresql://$host:$port/$dbName"

  def postgresConnectionString(config: DatabaseConfig) = {
    val host = config.PostgresServerName
    val port = config.PostgresPort
    val dbName = config.PostgresDbName
    val username = config.PostgresUsername
    val password = config.PostgresPassword
    JdbcConnectionString(postgresUrl(host, port, dbName), username, password)
  }

  def createPostgres(config: DatabaseConfig) = {
    val db = Database.forConfig("db.postgres", config.rootConfig)
    SqlDatabase(db, slick.driver.PostgresDriver, postgresConnectionString(config))
  }

  private def createEmbedded(config: DatabaseConfig): SqlDatabase = {
    val db = Database.forConfig("db.h2")
    SqlDatabase(db, slick.driver.H2Driver, JdbcConnectionString(embeddedConnectionStringFromConfig(config)))
  }

  def createEmbedded(connectionString: String): SqlDatabase = {
    val db = Database.forURL(connectionString)
    SqlDatabase(db, slick.driver.H2Driver, JdbcConnectionString(connectionString))
  }

}