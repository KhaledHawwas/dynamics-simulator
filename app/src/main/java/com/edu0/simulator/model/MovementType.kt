package com.edu0.simulator.model

enum class MovementType(val code: Int) {
    V_LAUNCH(0),
    V_FALL(1),
    V_LAUNCH_FROM_HEIGHT(2),
    ;

    companion object {
        fun fromCode(code: Int): MovementType {
            return when (code) {
                0 -> V_LAUNCH
                1 -> V_FALL
                else -> V_LAUNCH_FROM_HEIGHT
            }
        }
    }
}

val defaultVLaunch = Movement(
    MovementType.V_LAUNCH,
    Vi = 19.2,
    height0 = 0.0,
    isGravityRounded = false,
)
val defaultVFall = Movement(
    MovementType.V_FALL,
    Vi = 0.0,
    height0 = 50.0,
    isGravityRounded = false,
)
val defaultVLaunchFromHeight = Movement(
    MovementType.V_LAUNCH_FROM_HEIGHT,
    Vi = 10.0,
    height0 = 50.0,
    isGravityRounded = false,
)