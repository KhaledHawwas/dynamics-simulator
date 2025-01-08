package com.hawwas.simulator.model

class Movement(val movementType: MovementType) {
    var Vi: Double? = null
    var Vf: Double? = null
    var endTime: Double? = null
    var height0: Double? = null
    var maxHeight: Double? = null

    var isGravityRounded = false

    //data that is changeable
    var time = 0.0
        private set
    var Vc: Double = 0.0
        private set
    var height: Double = 0.0
        private set

    override fun toString(): String {
        return "Movement(movementType=$movementType,\n Vi=$Vi,\n Vf=$Vf,\n endTime=$endTime,\n height0=$height0,\n maxHeight=$maxHeight,\n isGravityRounded=$isGravityRounded,\n time=$time,\n Vc=$Vc,\n height=$height)"
    }
    fun setTime(t: Double) {
        time = t
        if (time > endTime!!) {
            time = endTime!!
            height = 0.0
        } else {
            height = height0!! + (Vi!! * time - gravity() * time * time * 0.5)
            height = if (height > 0) height else 0.0
            height = if (height < maxHeight!!) height else maxHeight!!
        }
        Vc = Vi!! - gravity() * time
    }

    fun reset() {
        time = 0.0
        height = height0!!
        Vc = Vi!!
    }

    fun isSolved(): Boolean {
        return Vi != null && Vf != null && height0 != null && maxHeight != null && endTime != null
    }

    fun gravity(): Double {
        return if (isGravityRounded) 10.0 else 9.8
    }


}