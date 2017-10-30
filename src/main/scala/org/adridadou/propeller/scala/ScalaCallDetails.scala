package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.values.{EthHash, TransactionReceipt}

import scala.concurrent.Future

case class ScalaCallDetails(result:Future[TransactionReceipt], txHash:EthHash)
