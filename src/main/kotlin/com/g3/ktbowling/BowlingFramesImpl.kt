package com.g3.ktbowling

class StrikeFrame(val rollsQueue: BowlingRolls) : BowlingFrame {

    override fun computeFrameScore(): TotalScore = rollsQueue.assignBonus(10, 2)
}

class StandardFrame(val rollsSum: Int) : BowlingFrame {
    override fun computeFrameScore(): TotalScore = TotalScoreWithValue(rollsSum)
}

class SpareFrame(val rollsQueue: BowlingRolls) : BowlingFrame {
    override fun computeFrameScore(): TotalScore = rollsQueue.assignBonus(10, 1)
}

class EmptyBowlingFrame : BowlingFrame {
    override fun computeFrameScore(): TotalScore = EmptyScore()
}
