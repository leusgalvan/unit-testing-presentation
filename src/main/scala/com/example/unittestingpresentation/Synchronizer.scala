package com.example.unittestingpresentation

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef}
import com.example.unittestingpresentation.Models.Account

import scala.concurrent.Future
import akka.pattern.ask
import akka.util.Timeout

case class SynchronizerAPI(synchronizer: ActorRef) {
  import Synchronizer._

  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  def synchronize(account: Account): Future[SynchronizeResponse] = {
    (synchronizer ? Synchronize(account)).mapTo[SynchronizeResponse]
  }
}

object Synchronizer {
  case class Synchronize(account: Account)

  sealed trait SynchronizeResponse
  case object SynchronizeSuccess
  case object SynchronizeError
}

class Synchronizer extends Actor {
  import Synchronizer._

  override def receive: Receive = {
    case Synchronize(account) =>
      if(math.random() < 0.5) {
        SynchronizeSuccess
      } else {
        SynchronizeError
      }
  }
}
