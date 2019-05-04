package com.example.unittestingpresentation

import com.example.unittestingpresentation.Models._
import org.scalatest.{FlatSpec, FunSuite, Matchers}
import org.specs2.mutable.Specification

class AccountFunSuiteUnitSpec extends FunSuite {
  test("Deposit should return a new account with the balance increased by the specified amount") {
    val accountBeforeDeposit = Account(1000)
    val accountAfterDeposit = accountBeforeDeposit.deposit(500)
    assert(accountAfterDeposit == Account(1500))
  }
}

class AccountFunSuiteWithMatchersUnitSpec extends FunSuite with Matchers {
  test("Withdraw should decrease the balance by the specified amount") {
    val accountBeforeWithdraw = Account(1000)
    val accountAfterWithdraw = accountBeforeWithdraw.withdraw(500)
    accountAfterWithdraw should be(Account(500))
    accountAfterWithdraw shouldBe Account(500)
    accountAfterWithdraw should equal(Account(500))
    accountAfterWithdraw shouldEqual Account(500)
    accountAfterWithdraw canEqual Account(500)
  }
}

class BankFlatSpecWithMatchersUnitSpec extends FlatSpec with Matchers {
  "Bank.addClient" should "throw an error when the client already exists" in {
    val leus = Client("Leus")
    val leusAccounts = List(Account(1000))
    val bank = Bank(Map(leus -> leusAccounts))

    // Checking an exception is thrown
    a [ClientAlreadyExistsException] should be thrownBy bank.addClient(leus)

    // Checking an exception is thrown and has given values
    val thrown = the [ClientAlreadyExistsException] thrownBy bank.addClient(leus)
    thrown.client shouldEqual leus
  }

  it should "should return a bank with the new client and no associated accounts" in {
    val leus = Client("Leus")
    val bank = Bank(Map())
    val bankWithLeus = bank.addClient(leus)
    bankWithLeus shouldEqual Bank(Map(leus -> Nil))
  }
}

class BankSpecificationWithMatchersUnitSpec extends Specification
  with org.specs2.matcher.Matchers {
  "Bank.addClient" should {
    "throw an error when the client already exists" in {
      val leus = Client("Leus")
      val leusAccounts = List(Account(1000))
      val bank = Bank(Map(leus -> leusAccounts))
      bank.addClient(leus) must throwA(ClientAlreadyExistsException(leus))
    }

    "should return a bank with the new client and no associated accounts" in {
      val leus = Client("Leus")
      val bank = Bank(Map())
      val bankWithLeus = bank.addClient(leus)
      bankWithLeus must_== Bank(Map(leus -> Nil))
    }
  }
}