package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.values.{EthCall, EthHash}

import scala.concurrent.Future

/**
  * Created by davidroon on 07.06.17.
  */
case class ScalaEthCall[T](ethCall:EthCall[T], converter:ScalaFutureConverter) {

  def getResult: Future[T] = converter.convert(ethCall.getResult)

  def getTransactionHash: EthHash = ethCall.getTransactionHash
}
