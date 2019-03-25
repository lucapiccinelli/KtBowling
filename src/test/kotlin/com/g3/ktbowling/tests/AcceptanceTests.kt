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
            "5,5" to "",
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

        var total = 0
        for (i in inputRolls.indices.filter { it % 2 == 0 }){
            if(i == inputRolls.size - 1){
                total = 0
                break
            }

            var frameValue = inputRolls[i] + inputRolls[i + 1]
            if(frameValue == 10){
                if(i == inputRolls.size - 2){
                    total = 0
                    break
                }

                frameValue += inputRolls[i + 2]
            }

            total += frameValue
        }

        println(if(total != 0) total else "")
    }

}