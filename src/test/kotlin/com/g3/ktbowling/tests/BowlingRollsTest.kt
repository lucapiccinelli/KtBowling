package com.g3.ktbowling.tests

import com.g3.ktbowling.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.math.exp
import kotlin.test.expect

class BowlingRollsTest{

    @TestFactory
    fun `test to assign a bonus, you must have enough Rolls`() = listOf(
            emptyList<Roll>() to false,
            listOf(Roll(1)) to true
    ).map{(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}"){
            val rolls = BowlingRolls(input)
            val newTotal = rolls.assignBonus(0, 1)
            Assertions.assertEquals(expected, newTotal.canContinue())
        }
    }

    @TestFactory
    fun `test new Bowling list of roll has frame logic`() = listOf(
            listOf(Roll(10)) to true,
            emptyList<Roll>() to false
    ).map{(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}")
        {
            val rolls = BowlingRolls(input)
            Assertions.assertEquals(expected, rolls.hasFrames())
        }
    }

    @TestFactory
    fun `test if i take out ten frames, i don't have anymore frames`() = listOf(
            Triple((0..11).map { Roll(10) }, (0..11),  false),
            Triple((0..19).map { Roll(1) },(0..11), false),
            Triple((0..11).map { Roll(1) },(0..5), false),
            Triple((0..19).map { Roll(1) }, (0..4), true)
    ).map{(input, times, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect that there are no more frame is ${expected}")
        {
            val rolls = BowlingRolls(input)
            times.forEach { rolls.takeNextFrame() }
            Assertions.assertEquals(expected, rolls.hasFrames())
        }
    }

    @TestFactory
    fun `test score can continue logic by input`()
        = listOf(
            (0..1).map { Roll(1) } to true,
            (0..0).map { Roll(1) } to false,

            (0..1).map { Roll(5) } to false,
            (0..2).map { Roll(5) } to false,
            (0..1).map { Roll(5) } + (0..1).map { Roll(1) } to true,

            (0..0).map { Roll(10) } to false,
            (0..0).map { Roll(10) } + (0..0).map { Roll(1) } to false,
            (0..0).map { Roll(10) } + (0..1).map { Roll(1) } to true
        ).map{(input, expected) ->
            DynamicTest.dynamicTest("Given ${input} i expect that can continue is ${expected}")
            {
                val rolls = BowlingRolls(input)
                var frame : BowlingFrame? = null
                while (rolls.hasFrames()){
                    frame = rolls.takeNextFrame()
                }
                val score = frame!!.computeFrameScore()

                Assertions.assertEquals(expected, score.canContinue())
            }
        }
}