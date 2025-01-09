package com.edu0.simulator.model

import kotlin.math.*

object VfHandler: VariableHandler() {
    override fun calc(data: Movement) {
        if (data.Vf != null) return
        if (data.height0 != null && data.Vi != null) {
            data.Vf = -sqrt(data.Vi!! * data.Vi!! + 2 * data.gravity() * data.height0!!)
        } else if (data.Vi != null && data.endTime != null) {
            data.Vf = data.Vi!! - data.gravity() * data.endTime!!
        } else if (data.maxHeight != null) {
            data.Vf = -sqrt(2 * data.gravity() * data.maxHeight!!)
        }
    }
}