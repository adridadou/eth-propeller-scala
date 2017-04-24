package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.solidity.{CompilationResult, SolidityContractDetails}

import scala.compat.java8.OptionConverters._

/**
  * Created by davidroon on 24.04.17.
  * This code is released under Apache 2 license
  */
case class SCompilationResult(result:CompilationResult) {
  def findContract(name:String):Option[SolidityContractDetails] = result.findContract(name).asScala
}
