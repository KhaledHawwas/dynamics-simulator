package com.edu0.simulator.model

import com.google.common.truth.Truth.*
import org.junit.*
import kotlin.math.*

class CalcTest {
    fun isClose(a: Double, b: Double): Boolean {
        return abs(a - b) < 0.03
    }

    @Test
    fun `V_LAUNCH vi 10 vf 10`() {
        val data = Movement(MovementType.V_LAUNCH)
        data.Vi = 10.0
        data.height0 = 0.0
        Calculator.calc(data)
        assertThat(data.isInvalid()).isFalse()
        assertThat(data.height0).isEqualTo(0.0) // No height change since launch and fall velocity are the same.
        val expectedMaxHeight = 10 * 10 / (2 * 9.8)
        assertThat(isClose(data.maxHeight!!, expectedMaxHeight)).isTrue()
    }

    @Test
    fun `V_LAUNCH vi 40 height0 120`() {
        val data = Movement(MovementType.V_LAUNCH)
        data.Vi = 0.0
        data.height0 = 78.4
        Calculator.calc(data)
        assertThat(data.isInvalid()).isFalse()
        assertThat(isClose(data.Vf!!, -39.2)).isTrue()
    }
    @Test
    fun `V_LAUNCH endTime 5 vi 0 height0 0`() {
        val data = Movement(MovementType.V_FALL)
        data.Vi = 0.0
        data.endTime = 5.0
        Calculator.calc(data)
        assertThat(data.isInvalid()).isFalse()
        assertThat(isClose(data.Vf!!, -49.0)).isTrue()
    }
    @Test
    fun `V_LAUNCH MaxHeight 20 height0 0`() {
        val data = Movement(MovementType.V_FALL)
        data.maxHeight = 20.0
        data.height0 = 0.0
        Calculator.calc(data)
        assertThat(data.isInvalid()).isFalse()
        assertThat(isClose(data.Vf!!, -19.79)).isTrue()
        assertThat(isClose(data.endTime!!, 4.04)).isTrue()
    }
    @Test
    fun `vertical launch with vi 0 and vf -9_8 should calculate correct values`() {
        val movementData = Movement(MovementType.V_LAUNCH).apply {
            Vi = 0.0
            Vf = -9.8
        }
        Calculator.calc(movementData)
        assertThat(movementData.isInvalid()).isFalse()
        assertThat(isClose(movementData.maxHeight!!, 4.9)).isTrue()
        assertThat(isClose(movementData.endTime!!, 1.0)).isTrue()
        assertThat(isClose(movementData.height0!!, 4.9)).isTrue()
    }

    @Test
    fun `vertical launch with height0 10, Vf -35_95 should calculate correct values`() {
        val initialHeight = 20.00
        val givenFinalVelocity = -35.95
        val expectedInitialVelocity = 30.00
        val expectedMaxHeight = 65.92
        val movementData = Movement(MovementType.V_LAUNCH).apply {
            height0 = initialHeight
            Vf = givenFinalVelocity
        }
        Calculator.calc(movementData)
        assertThat(movementData.isInvalid()).isTrue()
        assertThat(isClose(movementData.Vi!!, expectedInitialVelocity))
            .isTrue()
        assertThat(isClose(movementData.Vf!!, givenFinalVelocity))
            .isTrue()
        assertThat(isClose(movementData.maxHeight!!, expectedMaxHeight))
            .isTrue()
    }
    @Test
    fun `vertical launch with height0 10, end time 2 should calculate correct values`() {
        // Arrange
        val initialHeight = 20.00
        val timeElapsed = 6.73
        val expectedInitialVelocity = 30.00
        val expectedFinalVelocity = -35.95
        val expectedMaxHeight = 65.92
        val movementData = Movement(MovementType.V_LAUNCH).apply {
            height0 = initialHeight
            endTime = timeElapsed
        }
        Calculator.calc(movementData)
        assertThat(movementData.isInvalid()).isTrue()
        assertThat(isClose(movementData.Vi!!, expectedInitialVelocity))
            .isTrue()
        assertThat(isClose(movementData.Vf!!, expectedFinalVelocity))
            .isTrue()
        assertThat(isClose(movementData.maxHeight!!, expectedMaxHeight))
            .isTrue()
    }


}
