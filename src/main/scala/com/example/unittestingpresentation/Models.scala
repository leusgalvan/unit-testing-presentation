package com.example.unittestingpresentation

object Models {
  case class ClientAlreadyExistsException(client: Client)
    extends Exception(s"Client already exists: ${client.name}")

  case class Client(name: String)

  case class Account(balance: Double) {
    def deposit(amount: Double): Account = {
      Account(balance + amount)
    }

    def withdraw(amount: Double): Account = {
      Account(balance - amount)
    }
  }

  case class Bank(accountsByClient: Map[Client, List[Account]]) {
    def getClients(): Set[Client] = {
      accountsByClient.keySet
    }

    def addClient(client: Client): Bank = {
      accountsByClient.get(client) match {
        case Some(_) => throw ClientAlreadyExistsException(client)
        case None => Bank(accountsByClient + (client -> Nil))
      }
    }


  }
}

