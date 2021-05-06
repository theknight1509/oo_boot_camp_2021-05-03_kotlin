/*
 * Copyright (c) 2021 by Fred George
 * May be used freely except for training; license required for training.
 * @author Fred George  fredgeorge@acm.org
 */

package quantity

import quantity.Unit.Companion.EPSILON
import kotlin.math.absoluteValue

// Understands a specific measurement
open class Quantity internal constructor(amount: Number, private val unit: Unit) {
    private val amount = amount.toDouble()

    override fun equals(other: Any?) = this === other || other is Quantity && this.equals(other)

    private fun equals(other: Quantity) = this.isCompatible(other) &&
            (this.amount - convertedAmount(other)).absoluteValue < EPSILON

    private fun isCompatible(other: Quantity) = this.unit.isCompatible(other.unit)

    private fun convertedAmount(other: Quantity) = this.unit.convertedAmount(other.amount, other.unit)

    override fun hashCode() = unit.hashCode(amount)

    operator fun unaryPlus() = this

    operator fun unaryMinus() = Quantity(-amount, unit)

    infix operator fun plus(other: Quantity) = Quantity(this.amount + convertedAmount(other), unit)

    infix operator fun minus(other: Quantity) = this + -other
}