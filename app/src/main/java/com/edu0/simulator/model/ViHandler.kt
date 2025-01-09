package com.edu0.simulator.model

import kotlin.math.*

object ViHandler: VariableHandler() {

    override fun calc(data: Movement) {
        if (data.Vi != null) return
        if (data.endTime != null && data.Vf != null) {
            data.Vi = data.Vf!! - data.gravity() * data.endTime!!
        } else if (data.maxHeight != null && data.height0 != null) {
            data.Vi = sqrt(2 * data.gravity() * (data.maxHeight!! - data.height0!!))
        } else if (data.height0 != null && data.endTime != null) {
            data.Vi = (0.5 * data.gravity() * data.endTime!! * data.endTime!! - data.height0!!) / data.endTime!!
        }
    }
}