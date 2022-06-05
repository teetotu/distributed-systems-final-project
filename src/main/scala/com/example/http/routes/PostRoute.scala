package com.example.http.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.core.PostEntity
import com.example.core.posts.{KafkaProducer, PostService}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class PostRoute(
    postService: PostService
)(implicit executionContext: ExecutionContext)
    extends FailFastCirceSupport {

  import postService._

  val route: Route =
    (path("posts" / Segment) & get) { channel: String =>
      pathEndOrSingleSlash {
        get {
          complete(getPosts(channel).map(_.asJson))
        }
      }
    } ~
      (path("post") & post) {
        entity(as[PostEntity]) { post =>
          complete(createPost(post).map(KafkaProducer.send).map(_.asJson))
        }
      }
}
