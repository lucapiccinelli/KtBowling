package com.g3.ktbowling

import java.util.*

fun main(args: Array<String>) {
    val inputRolls = readInput(args)
    var total = playTheGame(ArrayDeque(inputRolls))
    printTheOutput(total)
}


private fun playTheGame(rolls: Queue<Roll>): Int {
    var total: Int = 0
    var frameCount = 0

    while (!rolls.isEmpty() && frameCount < 10) {
        val firstRoll = rolls.remove()

        if (!canTake(1, rolls)) {
            return -1
        }

        val (stop, runningTotal) = computeFrameValue(firstRoll, rolls, total)
        total = runningTotal
        if (stop) return -1

        frameCount++
    }

    return total
}

private fun computeFrameValue(firstRoll: Roll, rolls: Queue<Roll>, runningTotal: Int) : Pair<Boolean, Int>{
    if (firstRoll.rollValue == 10) {
        return assignBonus(rolls, firstRoll.rollValue, 2, runningTotal)
    } else {
        val frameValue = firstRoll.rollValue + rolls.remove().rollValue
        if (frameValue == 10) {
            return assignBonus(rolls, frameValue, 1, runningTotal)
        }
        return Pair(false, runningTotal + frameValue)
    }
}

private operator fun <E> Queue<E>.get(i: Int): E {
    return this.elementAt(i)
}

fun canTake(howMany: Int, fromCollection: Collection<Roll>): Boolean = fromCollection.size >= howMany

fun assignBonus(rolls: Collection<Roll>, partialValue: Int, bonusRolls: Int, totalIn: Int): Pair<Boolean,Int> {
    if (!canTake(bonusRolls, rolls)) {
        return Pair(true, totalIn + partialValue)
    }
    return Pair(false, totalIn + partialValue + computeBonus(rolls, bonusRolls))
}

private fun computeBonus(inputRolls: Collection<Roll>, bonus: Int) =
        inputRolls
                .take(bonus)
                .sumBy { it.rollValue }

private fun printTheOutput(total: Int) {
    println(if (total > 0) total else "")
}

private fun readInput(args: Array<String>) = args[0].split(",")
        .map(String::toInt)
        .map { Roll(it) }


class Roll(val rollValue: Int) {
}
