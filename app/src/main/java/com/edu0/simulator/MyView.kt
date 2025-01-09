package com.edu0.simulator

import android.content.*
import android.graphics.*
import android.os.*
import android.view.*
import androidx.annotation.*
import com.edu0.simulator.model.*
import kotlin.math.*

class MyView(context: Context, private val movement: Movement): View(context) {
    private lateinit var mPaint: Paint
    private var BOTTOM: Int = 0
    @RequiresApi(api = Build.VERSION_CODES.O) override fun draw(canvas: Canvas) {
        super.draw(canvas)
        setScale()
        mPaint = Paint()
        //public style
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        BOTTOM = height - 100
        //draw ground
        mPaint.color = this.resources.getColor(R.color.gray)
        canvas.drawRect(
            0f, BOTTOM.toFloat(), width.toFloat(), height.toFloat(),
            mPaint
        )
        drawBall(canvas, mPaint)
    }

    private fun drawBall(
        canvas: Canvas,
        mPaint: Paint,
    ) {
        if (!movement.isSolved()) return
        val maxHeight = movement.maxHeight!!
        val height0 = movement.height0!!
        val ballHeight = movement.height
        drawMaxHeightShadowBall(mPaint, canvas, maxHeight)
        if (height0 != 0.0) {
            drawHeightBar(
                mPaint,
                canvas,
                (BOTTOM - (height0 * scale).toInt()).toDouble(),
                BOTTOM.toDouble(),
                BALL_LEFT.toDouble() + BALL_WIDTH + 30
            )
        }
        //draw max height bar
        mPaint.color = resources.getColor(R.color.max_height, null)//todo
        if (movement.Vi != 0.0) {
            drawHeightBar(
                mPaint,
                canvas,
                (BOTTOM - (maxHeight * scale).toInt()).toDouble(),
                BOTTOM.toDouble(),
                BALL_LEFT.toDouble() - BALL_WIDTH - 30
            )
        }
        mPaint.style = Paint.Style.FILL

        mPaint.color = resources.getColor(R.color.primary, null)//todo
        canvas.drawOval(
            BALL_LEFT.toFloat(),
            (BOTTOM - (ballHeight * scale) - BALL_HEIGHT).toFloat(),
            (BALL_LEFT + BALL_WIDTH).toFloat(),
            (BOTTOM - (ballHeight * scale)).toFloat(),
            mPaint
        )
        drawArrow(mPaint, ballHeight, canvas)

    }

    private fun drawHeightBar(
        mPaint: Paint,
        canvas: Canvas,
        top: Double,
        bottom: Double,
        left: Double = BALL_LEFT.toDouble(),
    ) {
        // Set color for the pipe-like object
        val WIDTH = 50
        mPaint.color = resources.getColor(R.color.height0, null)

        // Calculate the center of the object horizontally
        val objectCenterX = (left + WIDTH / 2).toFloat()

        // Draw the vertical middle line
        canvas.drawLine(
            objectCenterX,
            top.toFloat(),
            objectCenterX,
            bottom.toFloat(),
            mPaint
        )

        // Draw the top horizontal line
        canvas.drawLine(
            left.toFloat(),
            top.toFloat(),
            (left + WIDTH).toFloat(),
            top.toFloat(),
            mPaint
        )

        // Draw the bottom horizontal line
        canvas.drawLine(
            left.toFloat(),
            bottom.toFloat(),
            (left + WIDTH).toFloat(),
            bottom.toFloat(),
            mPaint
        )

    }

    private fun drawMaxHeightShadowBall(
        mPaint: Paint,
        canvas: Canvas,
        maxHeight: Double,
    ) {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
        //TODO colors
        //draw max height
        mPaint.color = resources.getColor(R.color.secondary, null)//todo
        canvas.drawOval(
            BALL_LEFT.toFloat(),
            (BOTTOM - (maxHeight * scale).toInt() - BALL_HEIGHT).toFloat(),
            (BALL_LEFT + BALL_WIDTH).toFloat(),
            (BOTTOM - (maxHeight * scale).toInt()).toFloat(),
            mPaint
        )
    }

    private fun drawArrow(
        mPaint: Paint,
        ballHeight: Double,
        canvas: Canvas,
    ) {
        mPaint.color = this.resources.getColor(R.color.text_color)
        // Assuming Vc is a property of the ball object
        val vc = movement.Vc * 3

        // Constants for the arrow dimensions
        val arrowHeadSize = ((25 * abs(movement.Vc) / abs(movement.Vf!!)) + 10)
            .coerceIn(19.0, 40.0).toInt() // Resizes the arrowhead based on Vc percentage

        // Calculate the coordinates for the center of the ball
        val ballCenterX = (BALL_LEFT + BALL_WIDTH / 2).toFloat()
        val ballTop = (BOTTOM - (ballHeight * scale) - BALL_HEIGHT).toFloat()
        val ballBottom = (BOTTOM - (ballHeight * scale)).toFloat()

        // Determine the direction of the arrow (upward or downward)
        val arrowStartY = (ballTop + ballBottom) / 2
        val arrowEndY = arrowStartY - vc.toFloat() // Arrow extends upward or downward based on Vc

        // Calculate the arrow length
        val arrowLength = abs(arrowStartY - arrowEndY)

        if (arrowLength <= arrowHeadSize) {
            // Draw a dot instead of an arrow
            canvas.drawCircle(ballCenterX, arrowStartY, arrowHeadSize / 2f, mPaint)
        } else {
            // Draw the arrow line (vertical)
            canvas.drawLine(
                ballCenterX,
                arrowStartY,
                ballCenterX,
                arrowEndY + if (vc > 0) 10 else -10,
                mPaint
            )

            // Create a Path for the arrowhead
            val arrowPath = Path().apply {
                moveTo(ballCenterX, arrowEndY)
                if (vc > 0) {
                    // Arrowhead pointing upward
                    lineTo(ballCenterX - arrowHeadSize / 2, arrowEndY + arrowHeadSize)
                    lineTo(ballCenterX + arrowHeadSize / 2, arrowEndY + arrowHeadSize)
                } else {
                    // Arrowhead pointing downward
                    lineTo(ballCenterX - arrowHeadSize / 2, arrowEndY - arrowHeadSize)
                    lineTo(ballCenterX + arrowHeadSize / 2, arrowEndY - arrowHeadSize)
                }
                close()
            }

            // Draw the arrowhead
            canvas.drawPath(arrowPath, mPaint)
        }
    }


    private fun setScale() {
        val maxHeight = movement.maxHeight ?: 0.0
        // Use a logarithmic function to determine the scale dynamically
        scale = when {
            maxHeight <= 0 -> 45 // Default scale for non-positive maxHeight
            else -> {
                val baseScale = 45
                val scaleFactor = 2.5 // Adjust this factor to control scaling sensitivity
                (baseScale / (1 + log10(maxHeight + 1) * scaleFactor)).toInt()
            }
        }
    }


    companion object {
        const val BALL_WIDTH: Int = 100
        const val BALL_HEIGHT: Int = 100
        const val BALL_LEFT: Int = 400
        var scale: Int = 25

    }
}
