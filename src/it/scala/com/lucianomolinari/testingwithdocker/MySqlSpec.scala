package com.lucianomolinari.testingwithdocker

import java.sql.Connection

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Suite}

trait MySqlSpec extends BeforeAndAfterEach with BeforeAndAfterAll {
  this: Suite =>

  var connection: Connection = _

  override protected def beforeEach(): Unit = {
    connection.createStatement().execute("TRUNCATE TABLE customers")
    super.beforeEach()
  }

  override protected def beforeAll(): Unit = {
    connection = MysqlConnection.create("db_server", "root", "root")
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    super.afterEach()
    connection.close()
  }
}
