package com.g3.ktbowling.tests

import com.g3.ktbowling.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.math.exp
import kotlin.test.expect

class BowlingRollsTests{

    @Test
    fun `there are no more rolls`(){
        val rolls = BowlingRolls(emptyList())
        Assertions.assertTrue(rolls.isEmpty())
    }

    @Test
    fun `there are rolls`(){
        val rolls = BowlingRolls(listOf(Roll(1)))
        Assertions.assertFalse(rolls.isEmpty())
    }

    @Test
    fun `i can take this number of rolls`(){
        val rolls = BowlingRolls(listOf(Roll(1)))
        Assertions.assertTrue(rolls.canTake(1))
    }

    @Test
    fun `after taking a roll, there is one less in the list`(){
        val rolls = BowlingRolls(listOf(Roll(1)))
        rolls.takeNextRoll()
        Assertions.assertTrue(rolls.isEmpty())
    }

    @Test
    fun `if i compute a bonus, the size doesn't change`(){
        val rolls = BowlingRolls(listOf(Roll(1), Roll(2)))
        val bonus = rolls.computeBonus(2)

        Assertions.assertEquals(3, bonus)
        Assertions.assertFalse(rolls.isEmpty())
    }

    @Test
    fun `after taking 10 frames out, the list shoud be considered empty`(){
        val rolls = BowlingRolls((0..11).map { Roll(10) })
        (0..9).forEach {
            rolls.isEmpty()
            rolls.takeNextRoll()
        }
        Assertions.assertTrue(rolls.isEmpty())
    }

    @TestFactory
    fun `to assign a bonus, you must have enough Rolls`() = listOf(
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
    fun `new Bowling list of roll has frame logic`() = listOf(
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
    fun `if i take out ten frames, i don't have anymore frames`() = listOf(
            Triple((0..11).map { Roll(10) }, (0..11),  false),
            Triple((0..19).map { Roll(1) },(0..11), false),
            Triple((0..11).map { Roll(1) },(0..5), false),
            Triple((0..19).map { Roll(1) }, (0..4), true)
    ).map{(input, times, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect that there are no more frame")
        {
            val rolls = BowlingRolls(input)
            times.forEach { rolls.takeNextFrame() }
            Assertions.assertEquals(expected, rolls.hasFrames())
        }
    }

    @TestFactory
    fun `score can continue logic by input`()
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