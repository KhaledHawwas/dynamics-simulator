package com.edu0.simulator.model

object TextGenerator {
    fun generateText(movement: Movement): String {
        return "Movement type: ${movement.movementType}\n" +
                "Initial velocity: ${movement.Vi}\n" +
                "Final velocity: ${movement.Vf}\n" +
                "End time: ${movement.endTime}\n" +
                "Initial height: ${movement.height0}\n" +
                "Max height: ${movement.maxHeight}"
    }
}