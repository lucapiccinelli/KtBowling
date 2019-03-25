package com.g3.ktbowling.tests

import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

class AcceptanceTests {
    private lateinit var _myOut : OutputStream

    fun setup(){
        _myOut = ByteArrayOutputStream()
        System.setOut(PrintStream(_myOut))
    }

    fun tearDown(){
        _myOut.close()
    }

    inline fun eachTest(codeBlock: () -> Unit){
        try {
            setup()
            codeBlock()
        }finally {
            tearDown()
        }
    }

    @TestFactory
    fun `As a player i want to know my bowling point`() = listOf(
            "1,1" to "2",
            "5,5,1" to "",
            "5,5,1,1" to "13"
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}"){
            eachTest {
                play(arrayOf(input))
                Assertions.assertEquals("$expected${System.lineSeparator()}", _myOut.toString())
            }
        }
    }

    private fun play(args: Array<String>) {
        val inputRolls = args[0].split(",").map(String::toInt)
        val (firstRolls, secondRolls) = inputRolls.withIndex()
                .groupBy { it.index % 2 == 0 }
                .map { it.value.map { it.value } }

        if (firstRolls.size != secondRolls.size){
            println()
            return
        }

        val partialScores = firstRolls.zip(secondRolls)
                .map { (f, s) ->  f + s}

        val totalScore = partialScores.withIndex()
                .fold(0, {acc, v ->
                    when(v.value){
                        10 -> acc + v.value + firstRolls[v.index + 1]
                        else -> acc + v.value
                    }
        })

        println(totalScore)
    }

}