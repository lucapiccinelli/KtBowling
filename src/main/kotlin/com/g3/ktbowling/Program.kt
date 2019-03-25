package com.g3.ktbowling

import java.util.*

fun main(args: Array<String>) {
    val inputRolls = readInput(args)
    var total = playTheGame(ArrayDeque(inputRolls))
    printTheOutput(total)
}


private fun playTheGame(inputRolls: Queue<Int>): Int {
    var total: Int = 0
    var frameCount = 0
    while (!inputRolls.isEmpty() && frameCount < 10) {
        val firstRoll = inputRolls.remove()

        if (!canTake(1, inputRolls)) {
            return -1
        }

        var frameValue = 0
        if (firstRoll == 10) {

            val (newTotal, newFrameValue) = assignBonus(inputRolls, firstRoll, 2, total)
            total = newTotal
            frameValue = newFrameValue

        } else {

            frameValue = firstRoll + inputRolls.remove()

            if (frameValue == 10) {
                val (newTotal, newFrameValue) = assignBonus(inputRolls, frameValue, 1, total)
                total = newTotal
                frameValue = newFrameValue
            }

        }
        if (total == -1) break

        frameCount++
        total += frameValue
    }

    return total
}

private operator fun <E> Queue<E>.get(i: Int): E {
    return this.elementAt(i)
}

fun canTake(howMany: Int, fromCollection: Collection<Int>): Boolean = fromCollection.size >= howMany

fun assignBonus(rolls: Collection<Int>, partialValue: Int, bonusRolls: Int, totalIn: Int): Pair<Int,Int> {
    if (!canTake(bonusRolls, rolls)) {
        return Pair(-1, partialValue)
    }
    return Pair(totalIn, partialValue + computeBonus(rolls, bonusRolls))
}

private fun computeBonus(inputRolls: Collection<Int>, bonus: Int) =
        inputRolls.take(bonus).sum()

private fun printTheOutput(total: Int) {
    println(if (total > 0) total else "")
}

private fun readInput(args: Array<String>) = args[0].split(",").map(String::toInt)