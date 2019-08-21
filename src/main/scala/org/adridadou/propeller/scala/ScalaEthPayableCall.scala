package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.values.{EthPayableCall, EthValue}

/**
  * Created by davidroon on 07.06.17.
  */
case class ScalaEthPayableCall[T](ethCall:EthPayableCall[T], converter:ScalaFutureConverter) {
  def withValue(ethValue: EthValue): ScalaEthCall[T] = ScalaEthCall(ethCall.`with`(ethValue).get(), converter)
}
