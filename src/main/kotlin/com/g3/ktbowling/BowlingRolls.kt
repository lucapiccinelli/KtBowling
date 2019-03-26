package com.g3.ktbowling

import java.util.*

class BowlingRolls(rolls: Collection<Roll>) {
    private val _rollsQueue: Queue<Roll> = ArrayDeque(rolls)
    private var _frameCount: Int = 0


    fun assignBonus(rollValue: Int, bonusRolls: Int): TotalScore {
        if (!canTake(bonusRolls)) {
            return EmptyScore()
        }
        return TotalScoreWithValue(rollValue + computeBonus(bonusRolls))
    }

    fun hasFrames(): Boolean = _rollsQueue.size > 0 && _frameCount < 10

    fun takeNextFrame(): BowlingFrame {
        if(_rollsQueue.size == 0) return EmptyBowlingFrame()
        _frameCount++

        val firstRoll = takeNextRoll()
        if(firstRoll.rollValue == 10){
            return StrikeFrame(this)
        }

        if(!canTake(1)) return EmptyBowlingFrame()

        val rollsSum = firstRoll.rollValue + takeNextRoll().rollValue
        return when(rollsSum){
            10 -> SpareFrame(this)
            else -> StandardFrame(rollsSum)
        }
    }

    private fun takeNextRoll(): Roll = _rollsQueue.remove()

    private fun canTake(howMany: Int): Boolean = _rollsQueue.size >= howMany

    private fun computeBonus(bonusRolls: Int): Int =
        _rollsQueue
                .take(bonusRolls)
                .sumBy { it.rollValue }
}
