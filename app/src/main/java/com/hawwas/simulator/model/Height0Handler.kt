package com.hawwas.simulator.model

object Height0Handler : VariableHandler() {
    //yf - yi = Vi*t - 0.5*g*t^2
    //yi = yf - Vi*t + 0.5*g*t^2

    //Vf^2 = Vi^2 + 2*g*(yf-yi)
    //then yi=yf - ((Vf^2 - Vi^2) / (2*g))
    override fun calc(data: Movement) {
        if (data.height0 != null) return

        // Case: Calculate height0 using Vi and Vf or endTime
        if (data.maxHeight != null && data.Vi != null&&data.endTime!=null) {
            data.height0 = data.maxHeight!! - data.Vi!! * data.endTime!! + 0.5 * data.gravity() * data.endTime!! * data.endTime!!
        }
        else if (data.Vi != null && data.Vf != null && data.maxHeight != null) {
            data.height0 = data.maxHeight!! - ((data.Vf!! * data.Vf!! - data.Vi!! * data.Vi!!) / (2 * data.gravity()))
        }
    }
}
