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
    fun `Given a list of comma separated numbers, i expect an output in console`() = listOf(
            "1,4,4,5,6,4,5,5,10,0,1,7,3,6,4,10,2,8,6" to "133",
            "1,4,4,5,6,4,5,5,10,0,1,7,3,6,4,10,2,8" to ""
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Given ${input} i expect ${expected}"){
            eachTest {
                main(arrayOf(input))
                Assertions.assertEquals("$expected${System.lineSeparator()}", _myOut.toString())
            }
        }
    }
}