package com.example.unittestingpresentation

object Models {
  case class ClientAlreadyExistsException(client: Client)
    extends Exception(s"Client already exists: ${client.name}")

  case class ClientDoesNotExistException(client: Client)
    extends Exception(s"Client does not exist: ${client.name}")

  case class Client(name: String)

  case class Account(balance: Double) {
    def deposit(amount: Double): Account = {
      Account(balance + amount)
    }

    def withdraw(amount: Double): Account = {
      Account(balance - amount)
    }
  }

  case class Bank(accountsByClient: Map[Client, Set[Account]]) {
    def addClient(client: Client): Bank = {
      accountsByClient.get(client) match {
        case Some(_) => throw ClientAlreadyExistsException(client)
        case None => Bank(accountsByClient + (client -> Set()))
      }
    }

    def addAccountForClient(client: Client, account: Account) = {
      accountsByClient.get(client) match {
        case Some(currentAccountsForClient) =>
          val newAccountsForClient = currentAccountsForClient + account
          Bank(accountsByClient + (client -> newAccountsForClient))
        case None => throw ClientDoesNotExistException(client)
      }
    }
  }
}

