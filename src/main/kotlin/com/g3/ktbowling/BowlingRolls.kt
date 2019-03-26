package com.g3.ktbowling

import java.util.*

class BowlingRolls(rolls: Collection<Roll>) {
    private val _rollsQueue: Queue<Roll> = ArrayDeque(rolls)
    private var _frameCount: Int = 0

    fun takeNextRoll(): Roll = _rollsQueue.remove()

    fun isEmpty(): Boolean {
        _frameCount++
        return _rollsQueue.size == 0 || _frameCount > 10
    }

    fun canTake(howMany: Int): Boolean = _rollsQueue.size >= howMany

    fun computeBonus(bonusRolls: Int): Int =
        _rollsQueue
                .take(bonusRolls)
                .sumBy { it.rollValue }

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
        val secondRoll = takeNextRoll()

        val rollsSum = firstRoll.rollValue + secondRoll.rollValue
        when(rollsSum){
            10 -> return SpareFrame(this)
            else -> return StandardFrame(rollsSum)
        }
    }
}
