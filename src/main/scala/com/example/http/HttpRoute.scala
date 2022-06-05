package com.example.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.example.core.posts.PostService
import com.example.http.routes.PostRoute

import scala.concurrent.ExecutionContext

class HttpRoute(
    postService: PostService
)(implicit executionContext: ExecutionContext) {

  private val usersRouter = new PostRoute(postService)

  val route: Route =
    cors() {
      pathPrefix("v1") {
        usersRouter.route
      } ~
        pathPrefix("healthcheck") {
          get {
            complete("OK")
          }
        }
    }

}
