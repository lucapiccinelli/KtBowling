package com.g3.ktbowling.tests

import com.g3.ktbowling.main
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
            "5,5,1,1" to "13",
            "10,1,1" to "14",
            "10,1" to "",
            "10" to "",
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1" to "20",
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,1" to "29",
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,1,1" to "30",
            "10,10,10,10,10,10,10,10,10,10,10,10" to "300",
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,1,1" to "27",
            "10,10,10,1,1" to "65",
            "1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,1" to "",
            "10,10,10,1" to "",
            "1,4,4,5,6,4,5,5,10,0,1,7,3,6,4,10,2,8,6" to "133"
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}"){
            eachTest {
                main(arrayOf(input))
                Assertions.assertEquals("$expected${System.lineSeparator()}", _myOut.toString())
            }
        }
    }
}