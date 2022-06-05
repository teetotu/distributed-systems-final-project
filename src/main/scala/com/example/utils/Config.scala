package com.example.utils

import pureconfig.ConfigSource

case class Config(secretKey: String, http: HttpConfig, database: DatabaseConfig)

object Config {
  def load(): Config =
    ConfigSource.default.load[Config] match {
      case Right(config) => config
      case Left(error) =>
        throw new RuntimeException("Cannot read config file, errors:\n" + error.toList.mkString("\n"))
    }
}

private[utils] case class HttpConfig(host: String, port: Int)
private[utils] case class DatabaseConfig(jdbcUrl: String, username: String, password: String)
