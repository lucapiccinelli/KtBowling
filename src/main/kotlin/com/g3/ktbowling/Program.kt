package com.g3.ktbowling

fun main(args: Array<String>) {
    val inputRolls = readInput(args)
    var total = playTheGame(inputRolls)
    printTheOutput(total)
}


private fun playTheGame(inputRolls: List<Int>): Int {
    var total: Int = 0
    var i = 0
    var frameCount = 0
    while (i < inputRolls.size && frameCount < 10) {
        if (!canTake(1, inputRolls, i)) {
            return -1
        }

        var frameValue = 0
        if (inputRolls[i] == 10) {

            val (newTotal, newFrameValue) = assignBonus(inputRolls, 10, i, 2, total)
            total = newTotal
            frameValue = newFrameValue

            i++
        } else {

            frameValue = inputRolls[i] + inputRolls[i + 1]
            i++

            if (frameValue == 10) {
                val (newTotal, newFrameValue) = assignBonus(inputRolls, frameValue, i, 1, total)
                total = newTotal
                frameValue = newFrameValue
            }

            i++
        }
        if (total == -1) break

        frameCount++
        total += frameValue
    }

    return total
}

fun canTake(howMany: Int, fromCollection: List<Int>, fromIndex: Int): Boolean = fromIndex < fromCollection.size - howMany

fun assignBonus(rolls: List<Int>, partialValue: Int, currentIndex: Int, bonusRolls: Int, totalIn: Int): Pair<Int,Int> {
    if (!canTake(bonusRolls, rolls, currentIndex)) {
        return Pair(-1, partialValue)
    }
    return Pair(totalIn, partialValue + computeBonus(rolls, currentIndex, bonusRolls))
}

private fun computeBonus(inputRolls: List<Int>, fromIndex: Int, bonus: Int) =
        inputRolls.slice(fromIndex + 1..inputRolls.size - 1).take(bonus).sum()

private fun printTheOutput(total: Int) {
    println(if (total > 0) total else "")
}

private fun readInput(args: Array<String>) = args[0].split(",").map(String::toInt)