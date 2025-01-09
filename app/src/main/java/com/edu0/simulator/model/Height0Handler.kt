package com.edu0.simulator.model

object Height0Handler: VariableHandler() {
    override fun calc(data: Movement) {
        if (data.height0 != null) return
        if (data.maxHeight != null && data.Vi != null && data.endTime != null) {
            data.height0 =
                (data.Vi!! * data.endTime!! + 0.5 * data.gravity() * data.endTime!! * data.endTime!!)
        } else if (data.Vi != null && data.Vf != null && data.maxHeight != null) {
            data.height0 = ((data.Vf!! * data.Vf!! - data.Vi!! * data.Vi!!) / (2 * data.gravity()))
        }
    }
}
