package org.adridadou.propeller.scala

import java.lang.reflect.Method
import java.util.concurrent.CompletableFuture

import org.adridadou.ethereum.propeller.SmartContract
import org.adridadou.ethereum.propeller.converters.future.FutureConverter
import org.adridadou.ethereum.propeller.values.{CallDetails, Payable}

import scala.compat.java8.FutureConverters
import scala.concurrent.Future

/**
  * Created by davidroon on 18.04.17.
  * This code is released under Apache 2 license
  */
class ScalaFutureConverter() extends FutureConverter{

  def convert[T](completableFuture: CompletableFuture[T]): Future[T] = FutureConverters.toScala(completableFuture)

  override def getPayable(smartContract: SmartContract, objects: Array[AnyRef], method: Method): ScalaPayable[AnyRef] = ScalaPayable(new Payable[AnyRef](smartContract, method, objects), new ScalaFutureConverter())

  override def isPayableType(aClass: Class[_]): Boolean = classOf[ScalaPayable[_]].equals(aClass)

  override def isFutureType(aClass: Class[_]): Boolean = classOf[Future[_]].equals(aClass)

  override def isPayableTypeWithDetails(cls: Class[_]): Boolean = ???

  override def convertWithDetails(details: CallDetails, futureResult: CompletableFuture[_]): AnyRef = ???

  override def getPayableWithDetails(smartContract: SmartContract, arguments: Array[AnyRef], method: Method): AnyRef = ???

  override def isFutureTypeWithDetails(cls: Class[_]): Boolean = ???
}
