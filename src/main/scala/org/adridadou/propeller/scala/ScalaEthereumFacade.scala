package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.EthereumFacade
import org.adridadou.ethereum.propeller.solidity.converters.SolidityTypeGroup
import org.adridadou.ethereum.propeller.solidity.{SolidityContractDetails, SolidityEvent}
import org.adridadou.ethereum.propeller.swarm.SwarmHash
import org.adridadou.ethereum.propeller.values._
import org.adridadou.propeller.scala.encoders.ScalaNumberEncoder
import rx.lang.scala.Observable

import scala.compat.java8.OptionConverters._
import rx.lang.scala.JavaConversions._

import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.runtime.BoxedUnit

/**
  * Created by davidroon on 18.04.17.
  * This code is released under Apache 2 license
  */
class ScalaEthereumFacade(facade:EthereumFacade, converter:ScalaFutureConverter) {
  private val futureConverter = new ScalaFutureConverter

  def createContractProxy[T](abi: EthAbi, address: EthAddress, key: EthAccount)(implicit tag: ClassTag[T]):T = facade.createContractProxy(abi, address, key, tag.runtimeClass.asInstanceOf[Class[T]])
  def createContractProxy[T](contract: SolidityContractDetails, address: EthAddress, key: EthAccount)(implicit tag: ClassTag[T]):T = facade.createContractProxy(contract, address, key, tag.runtimeClass.asInstanceOf[Class[T]])
  def createContractProxy[T](address: EthAddress, account: EthAccount)(implicit tag: ClassTag[T]): T = facade.createContractProxy(address, account, tag.runtimeClass.asInstanceOf[Class[T]])

  def createSmartContract(address:EthAddress, account:EthAccount):ScalaSmartContract = ScalaSmartContract(facade.createSmartContract(address, account), futureConverter)
  def createSmartContract(contract:SolidityContractDetails, address:EthAddress, account:EthAccount):ScalaSmartContract = ScalaSmartContract(facade.createSmartContract(contract, address, account), futureConverter)
  def createSmartContract(abi:EthAbi, address:EthAddress, account:EthAccount):ScalaSmartContract = ScalaSmartContract(facade.createSmartContract(abi, address, account), futureConverter)

  def findEventDefinition[T](contract: SolidityContractDetails, eventName: String)(implicit tag: ClassTag[T]): Option[SolidityEvent[T]] = facade.findEventDefinition(contract,eventName, tag.runtimeClass.asInstanceOf[Class[T]]).asScala
  def findEventDefinition[T](abi: EthAbi, eventName: String)(implicit tag: ClassTag[T]): Option[SolidityEvent[T]] = facade.findEventDefinition(abi,eventName, tag.runtimeClass.asInstanceOf[Class[T]]).asScala
  def events():ScalaEthereumEventHandler = ScalaEthereumEventHandler(facade.events(), converter)
  def observeEvents[T](eventDefiniton: SolidityEvent[T], address: EthAddress): Observable[T] = facade.observeEvents(eventDefiniton, address)
  def observeEventsWIthInfo[T](eventDefiniton: SolidityEvent[T], address: EthAddress): Observable[EventInfo[T]] = facade.observeEventsWithInfo(eventDefiniton, address)

  def compile(solidityCode: SoliditySourceFile):SCompilationResult = SCompilationResult(facade.compile(solidityCode))
  def getEventsAtBlock[T](eventDefinition:SolidityEvent[T], address:EthAddress, number:Long):Seq[T] = facade.getEventsAtBlock(number, eventDefinition, address).asScala
  def getEventsAtBlock[T](eventDefinition:SolidityEvent[T], address:EthAddress, hash:EthHash):Seq[T] = facade.getEventsAtBlock(hash, eventDefinition, address).asScala
  def getEventsAtTransaction[T](eventDefinition:SolidityEvent[T], address:EthAddress, hash:EthHash):Seq[T] = facade.getEventsAtTransaction(hash, eventDefinition, address).asScala
  def getEventsAtBlockWithInfo[T](eventDefinition:SolidityEvent[T], address:EthAddress, number:Long):Seq[EventInfo[T]] = facade.getEventsAtBlockWithInfo(number, eventDefinition, address).asScala
  def getEventsAtBlockWithInfo[T](eventDefinition:SolidityEvent[T], address:EthAddress, hash:EthHash):Seq[EventInfo[T]] = facade.getEventsAtBlockWithInfo(hash, eventDefinition, address).asScala
  def getEventsAtTransactionWithInfo[T](eventDefinition:SolidityEvent[T], address:EthAddress, hash:EthHash):Seq[EventInfo[T]] = facade.getEventsAtTransactionWithInfo(hash, eventDefinition, address).asScala

  def getTransactionInfo(hash:EthHash):Option[TransactionInfo] = facade.getTransactionInfo(hash).asScala
  def publishContractWithValue(contract: SolidityContractDetails, account: EthAccount, value:EthValue, constructorArgs: AnyRef*): Future[EthAddress] = converter.convert(facade.publishContractWithValue(contract, account, value, constructorArgs:_*))
  def publishContract(contract: SolidityContractDetails, account: EthAccount, constructorArgs: AnyRef*): Future[EthAddress] = converter.convert(facade.publishContract(contract, account, constructorArgs:_*))
  def publishMetadataToSwarm(contract: SolidityContractDetails): SwarmHash = facade.publishMetadataToSwarm(contract)
  def sendEther(fromAccount: EthAccount, to: EthAddress, value: EthValue): Future[EthExecutionResult] = converter.convert(facade.sendEther(fromAccount, to, value))

  def addressExists(address: EthAddress): Boolean = facade.addressExists(address)

  def getBalance(addr: EthAddress): EthValue = facade.getBalance(addr)
  def getBalance(account: EthAccount): EthValue = facade.getBalance(account.getAddress)
  def getNonce(address: EthAddress): Nonce = facade.getNonce(address)
  def getCurrentBlockNumber: Long = facade.getCurrentBlockNumber

  def getCode(address: EthAddress): SmartContractByteCode = facade.getCode(address)

  def getMetadata(swarmMetadaLink: SwarmMetadaLink): SmartContractMetadata = facade.getMetadata(swarmMetadaLink)

}

object ScalaEthereumFacade {
  def apply(facade:EthereumFacade):ScalaEthereumFacade = {
    val converter = new ScalaFutureConverter()
    facade.addFutureConverter(converter)
    facade.addVoidType(classOf[BoxedUnit])
    facade.addVoidType(classOf[Unit])
    //handle conversion of BigInt and scala BigDecimal
    facade.addEncoder(SolidityTypeGroup.Number, new ScalaNumberEncoder)
    new ScalaEthereumFacade(facade, converter)
  }
}
