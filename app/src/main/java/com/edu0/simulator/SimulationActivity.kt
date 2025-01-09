package com.edu0.simulator

import android.os.*
import android.util.*
import android.view.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.core.view.*
import androidx.lifecycle.*
import com.edu0.simulator.databinding.*
import com.edu0.simulator.model.*
import kotlinx.coroutines.*

const val TAG = "MainActivity"

class SimulationActivity: AppCompatActivity() {
    var startTime = System.currentTimeMillis()
    var job: Job? = null
    lateinit var movement: Movement
    lateinit var myView: MyView
    lateinit var dialog: InputDialog
    lateinit var adapter: VariableItemAdapter
    var isPaused = false
    var heightVariable = VariableItem("الارتفاع", "h", 0.0, false)
    var VcVariable = VariableItem("السرعة الحالية", "Vc", 0.0, false)
    var timeVariable = VariableItem("الزمن", "t", 0.0, false)
    private lateinit var binding: ActivitySimulationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySimulationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dialog = InputDialog(this)
        val bundle = intent.getBundleExtra("movement")
        if (bundle == null) {
            finish()
            return
        }
        movement = Movement.fromBundle(bundle)
        if (!movement.isSolved()) {
            Calculator.calc(movement)
        }
        if (!movement.isSolved()) {
            Log.d(TAG, "Not solved")
            return
        }
        myView = MyView(this, movement)
        binding.container.addView(myView)
        binding.main.setOnLongClickListener {
            restartJob()
            true
        }
        binding.setHeightBtn.visibility = View.INVISIBLE//todo didn't implement
        binding.restartBtn.setOnClickListener {
            restartJob()
        }
        binding.main.setOnClickListener {
            togglePause()
        }

        adapter = VariableItemAdapter(
            this,
            listOf(
                VariableItem("السرعة الابتدائية", "Vi", movement.Vi!!),
                VariableItem("الارتفاع الابتدائي", "h0", movement.height0!!),
                VariableItem("السرعة النهائية", "Vf", movement.Vf!!),
                VariableItem("اقصى ارتفاع", "max height", movement.maxHeight!!),
                VariableItem("العجلة", "g", movement.gravity()),
                VariableItem("الزمن النهائي", "t", movement.endTime!!),
                heightVariable,
                VcVariable,
                timeVariable,
            )
        )
        binding.variablesRv.adapter = adapter
        binding.variablesRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.setTimeBtn.setOnClickListener {
            isPaused = true
            binding.pauseBtn.setImageResource(R.drawable.ic_play)
            dialog.show("Time") {
                Calculator.setTime(it, movement)
                resetNotConstant()
                myView.invalidate()
            }
        }
        binding.setVcBtn.setOnClickListener {
            isPaused = true
            binding.pauseBtn.setImageResource(R.drawable.ic_play)
            dialog.show("Vc") {
                Calculator.setVc(it, movement)
                resetNotConstant()
                myView.invalidate()
            }
        }
        binding.pauseBtn.setOnClickListener {
            togglePause()
        }


        restartJob()

    }

    fun togglePause() {
        isPaused = !isPaused
        binding.pauseBtn.setImageResource(if (isPaused) R.drawable.ic_play else R.drawable.ic_pause)
    }

    fun resetNotConstant() {
        heightVariable.value = movement.height
        VcVariable.value = movement.Vc
        timeVariable.value = movement.time
        adapter.notifyItemRangeChanged(6, 3)
    }

    fun restartJob() {
        job?.cancel()
        isPaused = true
        movement.reset()
        resetNotConstant()
        myView.invalidate()
        binding.pauseBtn.setImageResource(R.drawable.ic_play)

        job = lifecycleScope.launch {
            startTime = System.currentTimeMillis()
            var lastUpdateTime = startTime

            while (true) {
                if (isPaused) {
                    delay(100)  // Check every 100ms while paused
                    lastUpdateTime = System.currentTimeMillis()
                    continue
                }
                val currentTime = System.currentTimeMillis()
                val elapsedTime = (currentTime - lastUpdateTime) / 1000.0
                lastUpdateTime = currentTime
                Calculator.addTime(elapsedTime, movement)
                resetNotConstant()
                myView.invalidate()
                if (movement.time >= movement.endTime!!) {
                    Log.d(TAG, "Time is up")
                    return@launch
                }
                // Maintain a consistent 60fps frame rate
                val frameDuration = 20L
                val executionTime = System.currentTimeMillis() - currentTime
                val delayTime = (frameDuration - executionTime).coerceIn(1, frameDuration)
                delay(delayTime)
            }
        }
    }

}