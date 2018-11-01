package org.adridadou.propeller.scala.encoders

import org.adridadou.ethereum.propeller.solidity.SolidityType
import org.adridadou.ethereum.propeller.solidity.converters.encoders.{NumberEncoder, SolidityTypeEncoder}
import org.adridadou.ethereum.propeller.values.EthData

class ScalaNumberEncoder extends SolidityTypeEncoder{
  private val types = Seq(classOf[BigInt], classOf[BigDecimal])
  private val encoder = new NumberEncoder
  override def encode(arg: scala.Any, solidityType: SolidityType): EthData = arg match {
    case i:BigInt => encoder.encode(i.bigInteger,solidityType)
    case i:BigDecimal => encoder.encode(i.toBigInt().bigInteger,solidityType)
  }

  override def canConvert(cls: Class[_]): Boolean = types.contains(cls)
}
