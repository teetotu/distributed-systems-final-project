package com.example.core.posts

import com.example.core.PostEntity
import com.example.utils.db.DatabaseConnector

import scala.concurrent.{ExecutionContext, Future}

sealed trait PostStorage {

  def getPosts(channel: String): Future[Seq[PostEntity]]

  def savePost(post: PostEntity): Future[PostEntity]
}

class JdbcPostStorage(
    val databaseConnector: DatabaseConnector
)(implicit executionContext: ExecutionContext)
    extends PostTable
    with PostStorage {

  import databaseConnector._
  import databaseConnector.profile.api._

  def getPosts(channel: String): Future[Seq[PostEntity]] =
    db.run(posts.filter(_.channel === channel).result)

  def savePost(post: PostEntity): Future[PostEntity] =
    db.run(posts.insertOrUpdate(post)).map(_ => post)

}

//class InMemoryPostStorage extends PostStorage {
//
//  private var state: Seq[PostEntity] = Nil
//
//  override def getPosts(channel: String): Future[Seq[PostEntity]] =
//    Future.successful(state.filter(_.channel == channel))
//
//  override def savePost(post: PostEntity): Future[PostEntity] =
//    Future.successful {
//      state = state.filterNot(_.id == post.id)
//      state = state :+ post
//      post
//    }
//
//}
