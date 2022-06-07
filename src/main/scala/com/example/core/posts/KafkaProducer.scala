package com.example.core.posts

import com.example.core.PostEntity
import org.apache.kafka.clients.producer.{
  KafkaProducer,
  ProducerRecord,
  RecordMetadata
}

import java.util.Properties
import java.util.concurrent.Future

object KafkaProducer {

  val kafkaProducerProps: Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "6.tcp.ngrok.io:18990")
    props.put("key.serializer",
              "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",
              "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  val producer = new KafkaProducer[String, String](kafkaProducerProps)
  val topic = "posts-topic"

  def send(post: PostEntity): RecordMetadata = {
    println("kafka: sending event")
    val record = new ProducerRecord[String, String](topic, post.channel)
    producer.send(record).get()
  }
}
