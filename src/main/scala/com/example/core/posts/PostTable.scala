package com.example.core.posts

import com.example.utils.db.DatabaseConnector
import com.example.core.PostEntity
import com.example.utils.db.DatabaseConnector

private[posts] trait PostTable {

  protected val databaseConnector: DatabaseConnector
  import databaseConnector.profile.api._

  class Posts(tag: Tag) extends Table[PostEntity](tag, "posts") {
    def id = column[String]("id", O.PrimaryKey)
    def channel = column[String]("channel")
    def title = column[String]("title")

    def * =
      (id, channel, title) <> ((PostEntity.apply _).tupled, PostEntity.unapply)
  }

  protected val posts = TableQuery[Posts]

}
