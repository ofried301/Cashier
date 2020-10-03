package de.breitmeier.android.cashier.custom_views

import android.graphics.Color


fun getColorPalette(amount: Int): List<Int> {

    val colors = mutableListOf<Int>()
    colors.add(Color.BLUE)
    colors.add(Color.CYAN)
    colors.add(Color.GRAY)
    colors.add(Color.GREEN)
    colors.add(Color.DKGRAY)
    colors.add(Color.MAGENTA)
    colors.add(Color.RED)
    colors.add(Color.YELLOW)
    colors.add(Color.LTGRAY)
    colors.add(Color.WHITE)

    val colorList = mutableListOf<Int>()

    var counter = 0
    // Add colors to list
    while (colorList.size <= amount) {
        if (counter <= colors.size-1) {
            colorList.add(colors[counter])
        } else {
            counter = 0
        }
        counter++
    }

    return colorList
}













