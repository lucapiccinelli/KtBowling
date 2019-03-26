package com.g3.ktbowling

fun main(args: Array<String>) {
    val inputRolls = readInput(args)
    var total = BowlingGame.playTheGame(BowlingRolls(inputRolls))
    printTheOutput(total)
}

private fun printTheOutput(total: TotalScore) {
    println(total)
}

private fun readInput(args: Array<String>) = args[0].split(",")
        .map(String::toInt)
        .map { Roll(it) }


