package org.adridadou.propeller.scala


import org.adridadou.ethereum.propeller.EthereumFacade
import org.adridadou.ethereum.propeller.solidity.{SolidityContractDetails, SolidityEvent}
import org.adridadou.ethereum.propeller.swarm.SwarmHash
import org.adridadou.ethereum.propeller.values._
import rx.lang.scala.Observable

import scala.compat.java8.OptionConverters._
import rx.lang.scala.JavaConversions._
import scala.concurrent.Future

/**
  * Created by davidroon on 18.04.17.
  * This code is released under Apache 2 license
  */
class ScalaEthereumFacade(facade:EthereumFacade, converter:ScalaFutureConverter) {
  def createContractProxy[T](contract: SolidityContractDetails, address: EthAddress, key: EthAccount, clazz: Class[T]):T = facade.createContractProxy(contract, address, key, clazz)
  def createContractProxy[T](address: EthAddress, account: EthAccount, contractInterface: Class[T]): T = facade.createContractProxy(address, account, contractInterface)

  def findEventDefinition(contract: SolidityContractDetails, eventName: String, eventEntity: Class[_]): Option[SolidityEvent] = facade.findEventDefinition(contract,eventName, eventEntity).asScala
  def events():ScalaEthereumEventHandler = ScalaEthereumEventHandler(facade.events(), converter)
  def observeEvents[T](eventDefiniton: SolidityEvent, address: EthAddress, cls: Class[T]): Observable[T] = facade.observeEvents(eventDefiniton, address, cls)
  def compile(solidityCode: SoliditySourceFile):SCompilationResult = SCompilationResult(facade.compile(solidityCode))

  def publishContract(contract: SolidityContractDetails, account: EthAccount, constructorArgs: AnyRef*): Future[EthAddress] = converter.convert(facade.publishContract(contract, account, constructorArgs:_*))
  def publishMetadataToSwarm(contract: SolidityContractDetails): SwarmHash = facade.publishMetadataToSwarm(contract)
  def sendEther(fromAccount: EthAccount, to: EthAddress, value: EthValue): Future[EthExecutionResult] = converter.convert(facade.sendEther(fromAccount, to, value))

  def addressExists(address: EthAddress): Boolean = facade.addressExists(address)

  def getBalance(addr: EthAddress): EthValue = facade.getBalance(addr)
  def getBalance(account: EthAccount): EthValue = facade.getBalance(account.getAddress)
  def getNonce(address: EthAddress): Nonce = facade.getNonce(address)

  def getCode(address: EthAddress): SmartContractByteCode = facade.getCode(address)

  def getMetadata(swarmMetadaLink: SwarmMetadaLink): SmartContractMetadata = facade.getMetadata(swarmMetadaLink)

}

object ScalaEthereumFacade {
  def apply(facade:EthereumFacade):ScalaEthereumFacade = {
    val converter = new ScalaFutureConverter()
    facade.addFutureConverter(converter)
    new ScalaEthereumFacade(facade, converter)
  }
}
