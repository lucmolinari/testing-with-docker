package com.lucianomolinari.testingwithdocker

import org.scalatest.{FlatSpec, Matchers}

class CustomerRepositoryMySqlSpec extends FlatSpec with Matchers with MySqlSpec {

  trait Context {
    val repository = new CustomerRepositoryMySql(connection)
  }

  "A sequence with the existing customers ordered by id" should "be returned if there are customers" in new Context {
    repository.add(Customer(1, "Joe"))
    repository.add(Customer(2, "Mary"))

    repository.findAll shouldBe Seq(
      Customer(1, "Joe"),
      Customer(2, "Mary")
    )
  }

  "An empty sequence" should "be returned if there are no customers" in new Context {
    repository.findAll shouldBe empty
  }
}
