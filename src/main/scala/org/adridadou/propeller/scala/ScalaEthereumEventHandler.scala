package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.event.{BlockInfo, EthereumEventHandler}
import org.adridadou.ethereum.propeller.values.TransactionInfo
import rx.lang.scala.Observable

import scala.concurrent.Future
import rx.lang.scala.JavaConversions._

/**
  * Created by davidroon on 18.04.17.
  * This code is released under Apache 2 license
  */
case class ScalaEthereumEventHandler(handler:EthereumEventHandler, converter:ScalaFutureConverter) {
  def ready():Future[Void] = converter.convert(handler.ready())

  def currentBlockNumber:Long = handler.getCurrentBlockNumber

  def observeBlocks: Observable[BlockInfo] = handler.observeBlocks()

  def observeTransactions: Observable[TransactionInfo] = handler.observeTransactions()

}
