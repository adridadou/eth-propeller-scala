package org.adridadou.propeller.scala

import org.adridadou.ethereum.propeller.solidity.SolidityEvent
import org.adridadou.ethereum.propeller.solidity.abi.AbiEntry
import org.adridadou.ethereum.propeller.solidity.converters.decoders.SolidityTypeDecoder
import org.adridadou.ethereum.propeller.values.EventData

import scala.collection.JavaConverters._

case class ScalaRawSolidityEvent(description:AbiEntry, decoders:Seq[Seq[SolidityTypeDecoder]], eventParameters:Seq[Class[_]]) extends SolidityEvent[Seq[Any]](description, decoders.map(_.asJava).asJava){
	override def parseEvent(eventData: EventData): Seq[Any] = getDescription.decodeParameters(eventData, getDecoders, eventParameters.asJava).asScala
}
