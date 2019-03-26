package com.g3.ktbowling

class EmptyScore : TotalScore {
    override fun plus(totalScore: TotalScore) = EmptyScore()
    override fun plus(value: Int) = EmptyScore()
    override fun canContinue() = false
    override fun toString() = ""
}