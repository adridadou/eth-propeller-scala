package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.values.{EthValue, Payable}

import scala.concurrent.Future

/**
  * Created by davidroon on 18.04.17.
  * This code is released under Apache 2 license
  */
case class ScalaPayable[T](payable:Payable[T], converter:ScalaFutureConverter) {
  def withValue(ethValue: EthValue): Future[T] = converter.convert(payable.`with`(ethValue))
}
