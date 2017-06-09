package org.adridadou.propeller.scala

import java.lang.reflect.Method

import org.adridadou.ethereum.propeller.SmartContract
import org.adridadou.ethereum.propeller.solidity.SolidityFunction
import org.adridadou.ethereum.propeller.values.{CallDetails, EthAddress, EthValue}

import scala.collection.JavaConverters._
import scala.concurrent.Future

/**
  * Created by davidroon on 08.06.17.
  */
case class ScalaSmartContract(smartContract:SmartContract, futureConverter: ScalaFutureConverter) {
  def getFunctions: Seq[SolidityFunction] = smartContract.getFunctions.asScala
  def getConstructors: Seq[SolidityFunction] = smartContract.getConstructors.asScala
  def callFunction(value: EthValue, method: Method, args: Any*): Future[_] = futureConverter.convert(smartContract.callFunction(value,method, args))

  def callFunctionAndGetDetails(value: EthValue, method: Method, args: Any*): Future[CallDetails] = futureConverter.convert(smartContract.callFunctionAndGetDetails(value, method, args.map(_.asInstanceOf[Object]):_*))
  def callFunctionAndGetDetails(value: EthValue, func: SolidityFunction, args: Any*): Future[CallDetails] = futureConverter.convert(smartContract.callFunctionAndGetDetails(value, func, args.map(_.asInstanceOf[Object]):_*))

  def getAddress: EthAddress = smartContract.getAddress
}
