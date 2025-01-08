package com.hawwas.simulator.model

import com.google.common.truth.Truth.*
import org.junit.*
import kotlin.math.*

class CalcTest {
    fun isClose(a: Double, b: Double): Boolean {
        return abs(a - b) < 0.01
    }

    @Test
    fun `V_LAUNCH vi 10 vf 10`() {
        val data = Movement(MovementType.V_LAUNCH)
        data.Vi = 10.0
        data.height0 = 0.0

        // Calculate height0
        Calculator.calc(data)
        println("$data")
        assertThat(data.isSolved()).isTrue()
        assertThat(data.height0).isEqualTo(0.0) // No height change since launch and fall velocity are the same.
        // Calculate maxHeight
        val expectedMaxHeight = 10 * 10 / (2 * 9.8)
        assertThat(isClose(data.maxHeight!!, expectedMaxHeight)).isTrue()
    }

    @Test
    fun `V_LAUNCH vi 40 height0 120`() {
        val data = Movement(MovementType.V_LAUNCH)
        data.Vi = 0.0
        data.height0=78.4
        Calculator.calc(data)
        println("$data")
        assertThat(data.isSolved()).isTrue()
        assertThat(isClose(data.Vf!!, -39.2)).isTrue()
    }
    @Test
    fun `V_LAUNCH endTime 5 vi 0 height0 0`() {
        val data = Movement(MovementType.V_FALL)
        data.Vi = 0.0
        data.endTime=5.0
         // Object falls from ground level

        // Calculate maxHeight (should be zero as it starts from height 0)
        Calculator.calc(data)
        // Calculate endTime
        println("$data")
        assertThat(data.isSolved()).isTrue()
        assertThat(isClose(data.Vf!!, -49.0)).isTrue()
    }
    @Test
    fun `V_LAUNCH MaxHeight 20 height0 0`() {
        val data = Movement(MovementType.V_FALL)
        data.maxHeight = 20.0
        data.height0=0.0
        // Object falls from ground level

        // Calculate maxHeight (should be zero as it starts from height 0)
        Calculator.calc(data)
        // Calculate endTime
        println("$data")
        assertThat(data.isSolved()).isTrue()
        assertThat(isClose(data.Vf!!, -19.79)).isTrue()
        assertThat(isClose(data.endTime!!, 4.04)).isTrue()
    }




}
