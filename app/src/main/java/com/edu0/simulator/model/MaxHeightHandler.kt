package com.edu0.simulator.model

import kotlin.math.*

object MaxHeightHandler: VariableHandler() {
    override fun calc(data: Movement) {
        if (data.maxHeight != null) return
        if (data.Vi != null && data.height0 != null) {
            data.maxHeight = data.height0!! + (data.Vi!!.pow(2)) / (2 * data.gravity())
        } else if (data.height0 != null && data.endTime != null) {
            data.maxHeight = data.height0!! + (data.gravity() * data.endTime!!.pow(2)) / 8
        } else if (data.Vf != null) {
            data.maxHeight = (data.Vf!!.pow(2)) / (2 * data.gravity())
        }
    }
}