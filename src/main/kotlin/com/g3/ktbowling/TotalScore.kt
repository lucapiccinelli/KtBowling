package com.g3.ktbowling

interface TotalScore {
    val totalScore: Int
    fun canContinue(): Boolean
    operator fun plus(value: Int): TotalScore
    operator fun plus(computeFrameScore: TotalScore) : TotalScore
}