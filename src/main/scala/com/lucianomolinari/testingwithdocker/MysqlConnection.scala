package com.lucianomolinari.testingwithdocker

import java.sql.{Connection, DriverManager}

object MysqlConnection {

  def create(host: String, username: String, password: String): Connection = {
    val connectionUrl = s"jdbc:mysql://${host}/testing_with_docker_db"

    Class.forName("com.mysql.jdbc.Driver")

    DriverManager.getConnection(connectionUrl, username, password)
  }
}
