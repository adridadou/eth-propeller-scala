package org.adridadou.propeller.scala

import io.reactivex.Observable
import org.adridadou.ethereum.propeller.event.{BlockInfo, EthereumEventHandler}
import org.adridadou.ethereum.propeller.values.TransactionInfo

import scala.concurrent.Future

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
