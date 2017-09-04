package org.adridadou.propeller.scala.decoders

import java.lang.reflect.Type
import java.math.BigInteger

import org.adridadou.ethereum.propeller.solidity.converters.decoders.{NumberDecoder, SolidityTypeDecoder}
import org.adridadou.ethereum.propeller.values.EthData

class ScalaNumberDecoder extends SolidityTypeDecoder{
  private val types = Seq(classOf[BigInt])
  private val numberDecoder = new NumberDecoder
  override def canDecode(resultCls: Class[_]): Boolean = types.contains(resultCls)

  override def decode(index: Integer, data: EthData, resultType: Type): Number = BigInt(numberDecoder.decode(index,data,classOf[BigInteger]).asInstanceOf[BigInteger])
}
