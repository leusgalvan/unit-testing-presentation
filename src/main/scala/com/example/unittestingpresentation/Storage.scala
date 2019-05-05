package com.example.unittestingpresentation

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef}
import akka.util.Timeout
import akka.pattern.ask
import com.example.unittestingpresentation.Models.Client

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

object Storage {
  case class StoreClient(client: Client)
  sealed trait StorageResponse
  case class StorageSuccess(newId: Long) extends StorageResponse
  case object StorageError extends StorageResponse

  case class StorageFailedException(client: Client)
    extends Exception

  case class ClientStorageAPI(storage: ActorRef) {
    implicit val timeout = Timeout(5, TimeUnit.SECONDS)

    def storeClient(client: Client): Future[Long] = {
      (storage ? StoreClient(client: Client)) flatMap {
        case StorageSuccess(newId) =>
          Future.successful(newId)
        case StorageError =>
          Future.failed(StorageFailedException(client))
      }
    }
  }

  class ClientStorage extends Actor {
    override def receive: Receive = {
      case StoreClient(client) =>
        if(math.random() < 0.5) {
          sender ! StorageSuccess((math.random() * 1000).toLong)
        } else {
          sender ! StorageError
        }
    }
  }
}
