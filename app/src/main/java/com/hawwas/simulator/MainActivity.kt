package com.hawwas.simulator

import android.os.*
import android.util.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.core.view.*
import androidx.lifecycle.*
import com.hawwas.simulator.databinding.*
import com.hawwas.simulator.model.*
import kotlinx.coroutines.*

const val TAG = "MainActivity"

class MainActivity: AppCompatActivity() {
    var startTime = System.currentTimeMillis()
    var job: Job? = null
    lateinit var movement: Movement
    lateinit var myView: MyView
    var isPaused = false

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        movement = Movement(MovementType.V_LAUNCH)
        movement.Vi = 20.0
        movement.height0 = 50.0
        Calculator.calc(movement)
        if (!movement.isSolved()) {
            Log.d(TAG, "Not solved")
            return
        }
        myView = MyView(this, movement)
        binding.container.addView(myView)
        binding.main.setOnLongClickListener {
            restartJob()
            isPaused = false
            true
        }
        binding.main.setOnClickListener {
            isPaused=!isPaused
        }
        restartJob()

    }

    fun restartJob() {
        job?.cancel()
        job = lifecycleScope.launch {
            movement.reset()
            startTime = System.currentTimeMillis()
            myView.invalidate()
            var pausedTime = System.currentTimeMillis()
            while (true) {
                if (isPaused) {
                    delay(100)  // Suspend while paused, checks every 100ms
                    continue
                }
                startTime += (System.currentTimeMillis() - pausedTime)

                val time = System.currentTimeMillis() - startTime
                movement.setTime(time.toDouble() / 1000)
                myView.invalidate()
                binding.detailsTv.text = movement.toString()
                if (movement.time >= movement.endTime!!) {
                    Log.d(TAG, "Time is up")
                    return@launch
                }
                delay(15)
                pausedTime=System.currentTimeMillis()
            }
        }
    }
}