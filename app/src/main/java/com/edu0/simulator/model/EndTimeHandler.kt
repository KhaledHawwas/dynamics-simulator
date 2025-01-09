package com.edu0.simulator.model

object EndTimeHandler: VariableHandler() {
    override fun calc(data: Movement) {
        if (data.endTime != null) return
        if (data.Vi != null && data.Vf != null) {
            data.endTime = (-data.Vf!! + data.Vi!!) / data.gravity()
        }
    }
}
