package com.example.unittestingpresentation

import com.example.unittestingpresentation.Models.Client
import com.example.unittestingpresentation.Services.ClientService
import com.example.unittestingpresentation.Storage.{ClientStorageAPI, StorageFailedException}
import com.example.unittestingpresentation.Synchronization.{ClientSynchronizationAPI, SynchronizationFailedException}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.specs2.matcher.Matchers
import org.specs2.mutable.Specification

import scala.concurrent._
import scala.concurrent.duration.Duration

class ClientServiceUnitSpec extends Specification
  with Matchers
  with MockitoSugar {

  "ClientService.addClient" should {
    "return the new client id when both storage and synchronization succeed" in {
      val majo = Client("Majo")

      val synchronizationAPI = mock[ClientSynchronizationAPI]
      when(synchronizationAPI.synchronizeClient(majo)).thenReturn(Future.successful(()))

      val storageAPI = mock[ClientStorageAPI]
      when(storageAPI.storeClient(majo)).thenReturn(Future.successful(1L))

      val service = ClientService(storageAPI, synchronizationAPI)
      val result = Await.result(service.addClient(majo), Duration.Inf)

      result must_== 1L
    }

    "fail with the exception thrown by storage when storage fails" in {
      val majo = Client("Majo")

      val synchronizationAPI = mock[ClientSynchronizationAPI]
      when(synchronizationAPI.synchronizeClient(majo)).thenReturn(Future.successful(()))

      val storageAPI = mock[ClientStorageAPI]
      when(storageAPI.storeClient(majo)).thenReturn(Future.failed(StorageFailedException(majo)))

      val service = ClientService(storageAPI, synchronizationAPI)
      val result = Await.result(service.addClient(majo).failed, Duration.Inf)

      result must_== StorageFailedException(majo)
    }

    "fail with the exception thrown by synchronization when synchronization fails" in {
      val majo = Client("Majo")

      val synchronizationAPI = mock[ClientSynchronizationAPI]
      when(synchronizationAPI.synchronizeClient(majo)).thenReturn(Future.failed(SynchronizationFailedException(majo)))

      val storageAPI = mock[ClientStorageAPI]
      when(storageAPI.storeClient(majo)).thenReturn(Future.successful(1L))

      val service = ClientService(storageAPI, synchronizationAPI)
      val result = Await.result(service.addClient(majo).failed, Duration.Inf)

      result must_== SynchronizationFailedException(majo)
    }
  }
}
