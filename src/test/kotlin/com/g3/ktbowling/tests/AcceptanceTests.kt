package com.g3.ktbowling.tests

import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

class AcceptanceTests {
    private lateinit var _myOut : OutputStream

    @BeforeAll
    fun OneTimeSetup(){
        _myOut = ByteArrayOutputStream()
        System.setOut(PrintStream(_myOut))
    }

    @AfterAll
    fun OneTimeTearDown(){
        _myOut.close()
    }

    @TestFactory
    fun `As a player i want to know my bowling point`() = listOf(
            "1,1" to "2"
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}"){
            play(arrayOf(input))
            Assertions.assertEquals("$expected${System.lineSeparator()}", _myOut.toString())
        }
    }

    private fun play(args: Array<String>) {
        println("2")
    }

}