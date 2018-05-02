package com.lucianomolinari.testingwithdocker

import java.sql.{Connection, ResultSet}

import scala.annotation.tailrec

class CustomerRepositoryMySql(connection: Connection) extends CustomerRepository {

  private val insertStatement = connection.prepareStatement(
    """
      | INSERT INTO customers (id, name) VALUES (?, ?)
    """.stripMargin
  )

  private val findAllStatement = connection.prepareStatement(
    """
      | SELECT * FROM customers ORDER BY id
    """.stripMargin
  )

  override def add(customer: Customer): Customer = {
    insertStatement.setLong(1, customer.id)
    insertStatement.setString(2, customer.name)
    insertStatement.executeUpdate()
    customer
  }

  override def findAll: Seq[Customer] = {
    val rs = findAllStatement.executeQuery()
    rsToSeq(rs) { rs =>
      Customer(rs.getLong("id"), rs.getString("name"))
    }
  }

  private def rsToSeq[A](rs: ResultSet)(transformF: ResultSet => A): Seq[A] = {
    @tailrec
    def loop(acc: Seq[A]): Seq[A] = {
      if (!rs.next()) acc
      else loop(acc :+ transformF(rs))
    }
    loop(Seq.empty[A])
  }
}
