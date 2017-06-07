package org.adridadou.propeller.scala.converters.decoders

import java.lang.reflect.Type

import org.adridadou.ethereum.propeller.solidity.converters.decoders.SolidityTypeDecoder
import org.adridadou.ethereum.propeller.values.EthData

/**
  * Created by davidroon on 07.06.17.
  */
class UnitDecoder extends SolidityTypeDecoder {
  override def canDecode(resultCls: Class[_]): Boolean = resultCls.equals(classOf[Unit])

  override def decode(index: Integer, data: EthData, resultType: Type): AnyRef = Unit.box()
}
