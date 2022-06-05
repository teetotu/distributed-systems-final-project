package com.example

package object core {
  type PostId    = String

  final case class PostEntity(id: PostId, channel: String, postTitle: String) {
    require(id.nonEmpty, "id.empty")
    require(channel.nonEmpty, "firstName.empty")
    require(postTitle.nonEmpty, "firstName.empty")
  }

}
