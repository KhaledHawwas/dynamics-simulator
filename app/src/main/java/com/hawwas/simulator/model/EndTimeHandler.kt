package com.hawwas.simulator.model

import kotlin.math.*

object EndTimeHandler : VariableHandler() {
    override fun calc(data: Movement) {
        if (data.endTime != null) return

        // Case 1: Calculate endTime using Vi and Vf
        if (data.Vi != null && data.Vf != null) {
            data.endTime = (-data.Vf!! + data.Vi!!) / data.gravity()
        }

    }
}
