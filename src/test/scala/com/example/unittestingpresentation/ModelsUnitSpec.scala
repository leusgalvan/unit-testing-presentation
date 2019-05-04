package com.example.unittestingpresentation

import com.example.unittestingpresentation.Models._
import org.scalatest.{FunSuite, Matchers}

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
    accountAfterWithdraw should equal(Account(500))
    accountAfterWithdraw shouldBe Account(500)
    accountAfterWithdraw canEqual Account(500)
  }
}