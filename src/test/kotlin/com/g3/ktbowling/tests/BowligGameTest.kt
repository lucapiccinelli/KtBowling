package com.g3.ktbowling.tests

import com.g3.ktbowling.BowlingGame
import com.g3.ktbowling.BowlingRolls
import com.g3.ktbowling.Roll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class BowligGameTest{
    @TestFactory
    fun `As a player i want to know my bowling point`() = listOf(
            "1,1" to 2,
            "5,5,1" to -1,
            "5,5" to -1,
            "5,5,1,1" to 13,
            "10,1,1" to 14,
            "10,1" to -1,
            "10" to -1,
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1" to 20,
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,1" to 29,
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,1,1" to 30,
            "10,10,10,10,10,10,10,10,10,10,10,10" to 300,
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,1,1" to 27,
            "10,10,10,1,1" to 65,
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,1" to -1,
            "10,10,10,1" to -1
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}"){
            val rolls = input.split(",")
                    .map(String::toInt)
                    .map { Roll(it) }

            val score = BowlingGame.playTheGame(BowlingRolls(rolls))
            Assertions.assertEquals(expected, score.totalScore)
        }
    }
}

