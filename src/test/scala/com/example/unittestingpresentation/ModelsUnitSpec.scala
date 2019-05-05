package com.example.unittestingpresentation

import com.example.unittestingpresentation.Models._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, FunSuite, Matchers}
import org.specs2.mutable.Specification

class AccountFunSuiteUnitSpec extends FunSuite {
  test("Account.deposit should return a new account with the balance increased by the specified amount") {
    val accountBeforeDeposit = Account(1000)
    val accountAfterDeposit = accountBeforeDeposit.deposit(500)
    assert(accountAfterDeposit == Account(1500))
  }
}

class AccountFunSuiteWithMatchersUnitSpec extends FunSuite with Matchers {
  test("Account.withdraw should return a new account with the balance decreased by the specified amount") {
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
    val leusAccounts = Set(Account(1000))
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
    bankWithLeus shouldEqual Bank(Map(leus -> Set()))
  }
}

class BankSpecificationWithMatchersUnitSpec extends Specification
  with org.specs2.matcher.Matchers {
  
  "Bank.addClient" should {
    "throw an error when the client already exists" in {
      val leus = Client("Leus")
      val leusAccounts = Set(Account(1000))
      val bank = Bank(Map(leus -> leusAccounts))
      bank.addClient(leus) must throwA(ClientAlreadyExistsException(leus))
    }

    "should return a bank with the new client and no associated accounts" in {
      val leus = Client("Leus")
      val bank = Bank(Map())
      val bankWithLeus = bank.addClient(leus)
      bankWithLeus must_== Bank(Map(leus -> Set()))
    }
  }

  "Bank.addAccountForClient" should {
    "throw an error when the client does not exist" in {
      val leus = Client("leus")
      val account = Account(1000)
      val bank = Bank(Map())
      bank.addAccountForClient(leus, account) must throwA(ClientDoesNotExistException(leus))
    }

    "should return a bank where the given client has the new account" in {
      val leus = Client("Leus")
      val leusAccounts = Set(Account(1000))
      val bank = Bank(Map(leus -> leusAccounts))
      val bankWithNewLeusAccount = bank.addAccountForClient(leus, Account(2000))
      bankWithNewLeusAccount must_== Bank(Map(leus -> Set(Account(1000), Account(2000))))
    }
  }
}
//
//class BankWithMocksUnitSpec extends Specification
//  with org.specs2.matcher.Matchers
//  with MockitoSugar {
//
//  "Bank."
//}