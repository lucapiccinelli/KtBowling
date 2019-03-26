package com.g3.ktbowling

import java.util.*

fun main(args: Array<String>) {
    val inputRolls = readInput(args)
    var total = playTheGame(BowlingRolls(inputRolls))
    printTheOutput(total)
}


private fun playTheGame(bowlingRolls: BowlingRolls): TotalScore {
    var totalScore: TotalScore = TotalScoreWithValue(0)

    while (bowlingRolls.hasFrames() && totalScore.canContinue()) {
        var frame = bowlingRolls.takeNextFrame()
        totalScore += frame.computeFrameScore()
    }
//    while (!bowlingRolls.isEmpty() && totalScore.canContinue()) {
//        totalScore = computeFrameScore(bowlingRolls, totalScore)
//    }
    return totalScore
}

fun computeFrameScore(bowlingRolls: BowlingRolls, totalScore: TotalScore): TotalScore {
    val firstRoll = bowlingRolls.takeNextRoll()
    if (!bowlingRolls.canTake(1)) {
        return EmptyScore()
    }
    return computeFrameValue(firstRoll, bowlingRolls, totalScore)
}

private fun computeFrameValue(firstRoll: Roll, rolls: BowlingRolls, runningTotal: TotalScore) : TotalScore{
    if (firstRoll.rollValue == 10) {
        return rolls.assignBonus(firstRoll.rollValue, 2)
    } else {
        val frameValue = firstRoll.rollValue + rolls.takeNextRoll().rollValue
        if (frameValue == 10) {
            return rolls.assignBonus(frameValue, 1)
        }
        return runningTotal + frameValue
    }
}

private operator fun <E> Queue<E>.get(i: Int): E {
    return this.elementAt(i)
}

private fun printTheOutput(total: TotalScore) {
    println(total)
}

private fun readInput(args: Array<String>) = args[0].split(",")
        .map(String::toInt)
        .map { Roll(it) }


