package com.edu0.simulator.model

import kotlin.math.*

object Calculator {
    const val ROUNDS = 4//not specific number but it should be enough to solve the problem
    val handlers = listOf(
        ViHandler,
        Height0Handler,
        MaxHeightHandler,
        VfHandler,
        EndTimeHandler,
    )

    fun calc(data: Movement) {
        var i = 0
        while (i < handlers.size * ROUNDS && !data.isSolved()) {
            handlers[i % handlers.size].calc(data)
            ++i
        }
    }

    fun addTime(time: Double, data: Movement) {
        data.time += time
        setTime(data.time, data)
    }

    fun setTime(t: Double, movement: Movement) {
        movement.apply {
            time = t.coerceIn(0.0, endTime!!)
            if (time == endTime!!) {
                height = 0.0
            } else {
                height = height0!! + (Vi!! * time - gravity() * time * time * 0.5)
                height =height.coerceIn(0.0, maxHeight!!)
            }
            Vc = Vi!! - gravity() * time
        }
    }

    fun setVc(vc: Double, movement: Movement) {
        movement.apply {
            Vc = vc.coerceIn(Vf, Vi)
            time = (Vi!! - Vc) / gravity()
            setTime(time, movement)
        }
    }

    fun setHeight(h: Double, movement: Movement) {
        movement.apply {
            height = h.coerceIn(0.0, maxHeight!!)
            time = (Vi!! + sqrt(Vi!! * Vi!! - 2 * gravity() * (height0!! - height))) / gravity()
            time = time.coerceIn(0.0, endTime!!)
            Vc = Vi!! - gravity() * time
        }
    }

}