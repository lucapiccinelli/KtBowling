package com.g3.ktbowling.tests

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
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

    @Test
    fun `As a player i want to know my bowling point`(){
        play(arrayOf("1,1"))
        Assertions.assertEquals("2${System.lineSeparator()}", _myOut.toString())
    }

    private fun play(args: Array<String>) {
    }

}