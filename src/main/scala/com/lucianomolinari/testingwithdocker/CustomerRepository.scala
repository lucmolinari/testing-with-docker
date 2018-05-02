package com.lucianomolinari.testingwithdocker

trait CustomerRepository {

  def add(customer: Customer): Customer

  def findAll: Seq[Customer]
}
