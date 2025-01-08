package com.hawwas.simulator.model

object Calculator {
    const val ROUNDS=4//not specific number but it should be enough to solve the problem
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
}