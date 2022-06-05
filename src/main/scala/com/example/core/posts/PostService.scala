package com.example.core.posts

import com.example.core.PostEntity

import scala.concurrent.{ExecutionContext, Future}

class PostService(
    userPostStorage: PostStorage
)(implicit executionContext: ExecutionContext) {

  def getPosts(channel: String): Future[Seq[PostEntity]] =
    userPostStorage.getPosts(channel)

  def createPost(post: PostEntity): Future[PostEntity] =
    userPostStorage.savePost(post)
}
