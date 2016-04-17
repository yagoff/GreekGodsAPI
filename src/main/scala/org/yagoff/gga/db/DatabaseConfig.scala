package org.yagoff.gga.db

import com.typesafe.config.Config

trait DatabaseConfig {

  def rootConfig: Config

  val H2Url              = rootConfig.getString("db.h2.properties.url")
  val PostgresDSClass    = rootConfig.getString("db.postgres.dataSourceClass")
  val PostgresServerName = rootConfig.getString("db.postgres.properties.serverName")
  val PostgresPort       = rootConfig.getString("db.postgres.properties.portNumber")
  val PostgresDbName     = rootConfig.getString("db.postgres.properties.databaseName")
  val PostgresUsername   = rootConfig.getString("db.postgres.properties.user")
  val PostgresPassword   = rootConfig.getString("db.postgres.properties.password")
}
