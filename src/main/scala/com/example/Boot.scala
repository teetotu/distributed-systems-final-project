package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.example.core.posts.{JdbcPostStorage, PostService}
import com.example.http.HttpRoute
import com.example.utils.Config
import com.example.utils.db.{DatabaseConnector, DatabaseMigrationManager}

import scala.concurrent.{ExecutionContext, Future}

object Boot extends App {

  def startApplication(): Future[Http.ServerBinding] = {
    implicit val actorSystem: ActorSystem = ActorSystem()
    implicit val executor: ExecutionContext = actorSystem.dispatcher

    val config = Config.load()

    new DatabaseMigrationManager(
      config.database.jdbcUrl,
      config.database.username,
      config.database.password
    ).migrateDatabaseSchema()

    val databaseConnector = new DatabaseConnector(
      config.database.jdbcUrl,
      config.database.username,
      config.database.password
    )

    val postStorage = new JdbcPostStorage(databaseConnector)

    val usersService = new PostService(postStorage)
    val httpRoute = new HttpRoute(usersService)

    Http().newServerAt(config.http.host, sys.env.getOrElse("PORT", "9000").toInt).bind(httpRoute.route)
  }

  startApplication()

}
