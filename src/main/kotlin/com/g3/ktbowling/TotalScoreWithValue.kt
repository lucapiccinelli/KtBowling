package com.g3.ktbowling

class TotalScoreWithValue(override val totalScore: Int) : TotalScore {
    override fun plus(totalScore: TotalScore): TotalScore = totalScore + this.totalScore

    override fun plus(value: Int): TotalScore {
        return TotalScoreWithValue(totalScore + value)
    }

    override fun canContinue() = true
    override fun toString() = "${totalScore}"
}