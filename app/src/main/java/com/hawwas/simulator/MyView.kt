package com.hawwas.simulator

import android.content.*
import android.graphics.*
import android.os.*
import android.view.*
import androidx.annotation.*
import com.hawwas.simulator.model.*
import kotlin.math.*

class MyView(context: Context,private val ball: Movement): View(context) {
    private lateinit var mPaint: Paint
    @RequiresApi(api = Build.VERSION_CODES.O) override fun draw(canvas: Canvas) {
        super.draw(canvas)
        setScale()
        mPaint = Paint()
        //public style
        mPaint.isAntiAlias = true
        mPaint!!.isDither = true
        mPaint!!.strokeJoin = Paint.Join.ROUND
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.textSize = 43f

        drawBall(  canvas, mPaint!!)
        //draw ground
        mPaint!!.color = this.resources.getColor(R.color.gray)
        canvas.drawRect(
            0f, BOTTOM.toFloat(), width.toFloat(), height.toFloat(),
            mPaint!!
        )
    }

     private fun drawBall(
         canvas: Canvas,
         mPaint: Paint
    ) {
         if (!ball.isSolved())return
         val maxHeight= ball.maxHeight!!
         val height0= ball.height0!!
         val ballHeight= ball.height
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
        //draw height0
        mPaint.color = resources.getColor(R.color.height0, null)//todo
        canvas.drawOval(
            BALL_LEFT.toFloat(),
            (BOTTOM - (height0 * scale).toInt() - BALL_HEIGHT).toFloat(),
            (BALL_LEFT + BALL_WIDTH).toFloat(),
            (BOTTOM - (height0 * scale).toInt()).toFloat(),
            mPaint
        )

        if (ball.movementType ==MovementType.V_LAUNCH_FROM_HEIGHT) {
            mPaint.color = resources.getColor(R.color.secondary, null)//todo
            canvas.drawOval(
                BALL_LEFT.toFloat(),
                (BOTTOM - BALL_HEIGHT).toFloat(), (BALL_LEFT + BALL_WIDTH).toFloat(),
                BOTTOM.toFloat(), mPaint
            )
        }

        mPaint.style = Paint.Style.FILL

        //draw text
        mPaint.color = this.resources.getColor(R.color.text_color)
        //ball.drawText(canvas, mPaint) todo

        //draw main ball
        mPaint.color = resources.getColor(R.color.primary, null)//todo
        canvas.drawOval(
            BALL_LEFT.toFloat(),
            (BOTTOM - (ballHeight * scale) - BALL_HEIGHT).toFloat(),
            (BALL_LEFT + BALL_WIDTH).toFloat(),
            (BOTTOM - (ballHeight * scale)).toFloat(),
            mPaint
        )
    }

    private fun drawArrow(
        paint: Paint,
        canvas: Canvas,
        from_x: Float,
        from_y: Float,
        to_x: Float,
        to_y: Float
    ) {
        val anglerad: Float

        val radius = 10f
        val angle = 15f

        //some angle calculations
        anglerad = (Math.PI * angle / 180.0f).toFloat()
        val lineangle =
            atan2((to_y - from_y).toDouble(), (to_x - from_x).toDouble()).toFloat()

        //tha line
        val scale = 50f
        canvas.scale(scale, scale)
        canvas.drawLine(from_x / scale, from_y / scale, to_x / scale, to_y / scale, paint)

        //tha triangle
        val path = Path()

        path.fillType = Path.FillType.EVEN_ODD
        path.moveTo(to_x, to_y)
        path.lineTo(
            (to_x - radius * cos(lineangle - (anglerad / 2.0))).toFloat(),
            (to_y - radius * sin(lineangle - (anglerad / 2.0))).toFloat()
        )
        path.lineTo(
            (to_x - radius * cos(lineangle + (anglerad / 2.0))).toFloat(),
            (to_y - radius * sin(lineangle + (anglerad / 2.0))).toFloat()
        )
        path.close()

        canvas.drawPath(path, paint)
    }

    private fun setScale() {
        val maxHeight = ball.maxHeight!!
        scale = if (maxHeight < 1) {
            60
        } else if (maxHeight < 5) {
            40
        } else if (maxHeight < 20) {
            20
        } else if (maxHeight < 50) {
            15
        } else if (maxHeight < 200) {
            5
        } else {
            1
        }
    }

    companion object {
        const val BALL_WIDTH: Int = 100
        const val BALL_HEIGHT: Int = 100
        const val BOTTOM: Int = 2000
        const val BALL_LEFT: Int = 450
        var scale: Int = 25

        fun drawText(s: Canvas, mPaint: Paint?, title: String?, value: String?, pos: Int) {
            s.drawText(title!!, (45 + 500).toFloat(), (50 + 85 * pos).toFloat(), mPaint!!)
            s.drawText(value!!, (100 + 300).toFloat(), (50 + 85 * pos).toFloat(), mPaint)
        }
    }
}
