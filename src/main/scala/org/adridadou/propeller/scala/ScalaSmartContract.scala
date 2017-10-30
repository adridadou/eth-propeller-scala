package org.adridadou.propeller.scala

import java.lang.reflect.Method

import org.adridadou.ethereum.propeller.SmartContract
import org.adridadou.ethereum.propeller.solidity.SolidityFunction
import org.adridadou.ethereum.propeller.values.{EthAddress, EthValue}

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by davidroon on 08.06.17.
  */
case class ScalaSmartContract(smartContract:SmartContract, futureConverter: ScalaFutureConverter) {
  def getFunctions: Seq[SolidityFunction] = smartContract.getFunctions.asScala
  def getConstructors: Seq[SolidityFunction] = smartContract.getConstructors.asScala
  def callFunction(value: EthValue, method: Method, args: Any*): Future[_] = futureConverter.convert(smartContract.callFunction(value,method, args))

  def callFunctionAndGetDetails(value: EthValue, method: Method, args: Any*)(implicit ex:ExecutionContext): Future[ScalaCallDetails] = {
    futureConverter.convert(smartContract.callFunctionAndGetDetails(value, method, args.map(_.asInstanceOf[Object]):_*))
      .map(details => ScalaCallDetails(result = futureConverter.convert(details.getResult), txHash = details.getTxHash))
  }
  def callFunctionAndGetDetails(value: EthValue, func: SolidityFunction, args: Any*)(implicit ex:ExecutionContext): Future[ScalaCallDetails] = {
    futureConverter.convert(smartContract.callFunctionAndGetDetails(value, func, args.map(_.asInstanceOf[Object]):_*))
      .map(details => ScalaCallDetails(result = futureConverter.convert(details.getResult), txHash = details.getTxHash))
  }

  def getAddress: EthAddress = smartContract.getAddress
}
