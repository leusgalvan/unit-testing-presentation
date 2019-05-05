package com.example.unittestingpresentation

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef}
import com.example.unittestingpresentation.Models.Client

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask
import akka.util.Timeout
import scala.util.{Success, Failure}

object Synchronization {
  case class SynchronizeClient(client: Client)
  sealed trait SynchronizeResponse
  case object SynchronizeSuccess extends SynchronizeResponse
  case object SynchronizeError extends SynchronizeResponse

  case class SynchronizationFailedException(client: Client)
    extends Exception

  case class ClientSynchronizationAPI(synchronizer: ActorRef) {
    private implicit val timeout = Timeout(5, TimeUnit.SECONDS)

    def synchronizeClient(client: Client): Future[Unit] = {
      (synchronizer ? SynchronizeClient(client: Client)) flatMap {
        case SynchronizeSuccess =>
          Future.successful(())
        case SynchronizeError =>
          Future.failed(SynchronizationFailedException(client))
      }
    }
  }

  class ClientSynchronizer extends Actor {
    override def receive: Receive = {
      case SynchronizeClient(client) =>
        if(math.random() < 0.5) {
          sender ! SynchronizeSuccess
        } else {
          sender ! SynchronizeError
        }
    }
  }
}
