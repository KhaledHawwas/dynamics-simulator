package com.hawwas.simulator.model

import kotlin.math.*

object MaxHeightHandler : VariableHandler() {
    override fun calc(data: Movement) {
        if (data.maxHeight != null) return
        //get max height from Vf only
        if (data.Vf != null) {
            data.maxHeight = (data.Vf!! * data.Vf!!) / (2 * data.gravity())
        }else if (data.Vi != null&&data.endTime!=null&&data.height0!=null) {
            data.maxHeight =
                (data.Vi!! * data.endTime!! + 0.5 * data.gravity() * data.endTime!! * data.endTime!!) +
                        data.height0!!
        }else if (data.Vi != null &&data.height0!=null) {
            data.maxHeight = data.height0!! + (( data.Vi!! * data.Vi!!) / (2 * data.gravity()))
        }
    }
}
