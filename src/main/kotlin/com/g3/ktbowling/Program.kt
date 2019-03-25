package com.g3.ktbowling

fun main(args: Array<String>) {
    val inputRolls = args[0].split(",").map(String::toInt)

    var total = 0
    var i = 0
    var frameCount = 0
    while (i < inputRolls.size && frameCount < 10){
        if(i == inputRolls.size - 1){
            total = 0
            break
        }

        var frameValue = 0
        if (inputRolls[i] == 10){
            if(i == inputRolls.size - 2){
                total = 0
                break
            }

            frameValue = 10 + inputRolls[i + 1] + inputRolls[i + 2]
            i++
        }else{
            frameValue = inputRolls[i] + inputRolls[i + 1]
            if(frameValue == 10){
                if(i == inputRolls.size - 2){
                    total = 0
                    break
                }

                frameValue += inputRolls[i + 2]
            }
            i += 2
        }

        frameCount++
        total += frameValue
    }

    println(if(total != 0) total else "")
}