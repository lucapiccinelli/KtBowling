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
            "5,5,1" to "11"
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}"){
            eachTest {
                play(arrayOf(input))
                Assertions.assertEquals("$expected${System.lineSeparator()}", _myOut.toString())
            }
        }
    }

    private fun play(args: Array<String>) {
        var totalScore = args[0].split(",").map(String::toInt).sum()
        println(totalScore)
    }

}