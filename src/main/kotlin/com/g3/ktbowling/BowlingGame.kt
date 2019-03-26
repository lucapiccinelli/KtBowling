package com.g3.ktbowling

class BowlingGame {
    companion object {
        fun playTheGame(bowlingRolls: BowlingRolls): TotalScore {
            var totalScore: TotalScore = TotalScoreWithValue(0)

            while (bowlingRolls.hasFrames() && totalScore.canContinue()) {
                var frame = bowlingRolls.takeNextFrame()
                totalScore += frame.computeFrameScore()
            }
            return totalScore
        }
    }
}