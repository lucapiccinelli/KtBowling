package com.g3.ktbowling

class TotalScoreWithValue(val score: Int) : TotalScore {
    override fun plus(totalScore: TotalScore): TotalScore = totalScore + score

    override fun plus(value: Int): TotalScore {
        return TotalScoreWithValue(score + value)
    }

    override fun canContinue() = true
    override fun toString() = "${score}"
}