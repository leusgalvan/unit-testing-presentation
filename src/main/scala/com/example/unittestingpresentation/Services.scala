package com.example.unittestingpresentation

import akka.actor.ActorRef
import com.example.unittestingpresentation.Models.Client
import com.example.unittestingpresentation.Storage.ClientStorageAPI
import com.example.unittestingpresentation.Synchronization.ClientSynchronizationAPI

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

object Services {
  case class ClientService(clientStorageAPI: ClientStorageAPI,
                           clientSynchronizerAPI: ClientSynchronizationAPI) {
    def addClient(client: Client): Future[Long] = {
      for {
        newId <- clientStorageAPI.storeClient(client)
        _ <- clientSynchronizerAPI.synchronizeClient(client)
      } yield {
        newId
      }
    }
  }
}
