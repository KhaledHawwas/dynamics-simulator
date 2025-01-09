package com.edu0.simulator.model

import android.os.*


data class Movement(
    val movementType: MovementType,
    var Vi: Double? = null,
    var Vf: Double? = null,
    var endTime: Double? = null,
    var height0: Double? = null,
    var maxHeight: Double? = null,
    var isGravityRounded: Boolean = false,

    ) {
    //data that is changeable
    var time: Double = 0.0
    var Vc: Double = 0.0
    var height: Double = 0.0
    fun reset() {
        time = 0.0
        height = height0!!
        Vc = Vi!!
    }

    override fun toString(): String {
        return "Movement(movementType=$movementType,\n Vi=$Vi,\n Vf=$Vf,\n endTime=$endTime,\n height0=$height0,\n maxHeight=$maxHeight,\n isGravityRounded=$isGravityRounded,\n time=$time,\n Vc=$Vc,\n height=$height)"
    }

    fun toBundle(): Bundle {
        return Bundle().apply {
            putInt("movementType", movementType.code)
            putDouble("Vi", Vi ?: Double.NaN)
            putDouble("Vf", Vf ?: Double.NaN)
            putDouble("endTime", endTime ?: Double.NaN)
            putDouble("height0", height0 ?: Double.NaN)
            putDouble("maxHeight", maxHeight ?: Double.NaN)
            putBoolean("isGravityRounded", isGravityRounded)
        }
    }

    companion object {
        fun fromBundle(bundle: Bundle): Movement {
            return Movement(
                MovementType.fromCode(bundle.getInt("movementType")),
                nanToNull(bundle.getDouble("Vi")),
                nanToNull(bundle.getDouble("Vf")),
                nanToNull(bundle.getDouble("endTime")),
                nanToNull(bundle.getDouble("height0")),
                nanToNull(bundle.getDouble("maxHeight")),
                bundle.getBoolean("isGravityRounded")
            )
        }

        fun nanToNull(d: Double): Double? {
            return if (d.isNaN()) null else d
        }
    }

    fun isSolved(): Boolean {
        return Vi != null && Vf != null && height0 != null && maxHeight != null && endTime != null
    }
    fun isInvalid():Boolean{
        if (!isSolved())
            return true

        return height0!!>=0 &&
                maxHeight!!>= height0!! &&
                -Vf!! >= Vi!! &&
                endTime!! > 0

    }

    fun gravity(): Double {
        return if (isGravityRounded) 10.0 else 9.8
    }


}