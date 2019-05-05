package com.example.unittestingpresentation

object Models {
  case class ClientAlreadyExistsException(client: Client)
    extends Exception

  case class ClientDoesNotExistException(client: Client)
    extends Exception

  case class Client(name: String)

  case class Account(id: Long, balance: Double) {
    def deposit(amount: Double): Account = {
      Account(id, balance + amount)
    }

    def withdraw(amount: Double): Account = {
      Account(id, balance - amount)
    }
  }

  case class Bank(id: Long, accountsByClient: Map[Client, Set[Account]]) {
    def addClient(client: Client): Bank = {
      accountsByClient.get(client) match {
        case Some(_) => throw ClientAlreadyExistsException(client)
        case None => Bank(id, accountsByClient + (client -> Set()))
      }
    }

    def getClientByName(name: String): Option[Client] = {
      accountsByClient.keySet.find(_.name == name)
    }
  }
}

