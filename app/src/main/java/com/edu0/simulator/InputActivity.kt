package com.edu0.simulator

import android.content.*
import android.os.*
import android.text.*
import android.util.*
import android.view.*
import android.widget.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.core.view.*
import com.edu0.simulator.databinding.*
import com.edu0.simulator.model.*
import kotlin.math.*

class InputActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private lateinit var movement: Movement
    private lateinit var checkBoxes: List<CheckBox>
    private lateinit var variablesEt: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bundle = intent.getBundleExtra("movement")
        if (bundle == null) {
            finish()
            return
        }
        movement = Movement.fromBundle(bundle)
        Calculator.calc(movement)
        checkBoxes = listOf(
            binding.h0Check,
            binding.viCheck,
            binding.vfCheck,
            binding.endTimeCheck,
            binding.maxHeightCheck
        )
        variablesEt =
            listOf(binding.h0Et, binding.viEt, binding.vfEt, binding.endTimeEt, binding.maxHeightEt)



        when (movement.movementType) {

            MovementType.V_FALL -> {
                binding.titleTv.text = getString(R.string.vertical_fall)
                binding.minusViTv.visibility = View.VISIBLE
                binding.h0Check.isChecked = true
                binding.h0Et.isEnabled = true
            }

            MovementType.V_LAUNCH -> {
                binding.titleTv.text = getString(R.string.vertical_launch)
                binding.h0Check.isEnabled = false
                binding.h0Check.isChecked = true
                binding.h0Et.setText("0")
                binding.h0Et.isEnabled = false

            }

            MovementType.V_LAUNCH_FROM_HEIGHT -> {
                binding.titleTv.text = getString(R.string.launch_from_height)
                binding.h0Check.isChecked = true
                binding.h0Et.isEnabled = true
            }
        }
        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                variablesEt[index].isEnabled = isChecked
                refresh()
            }
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.startBtn.setOnClickListener {
            startActivity(
                Intent(this, SimulationActivity::class.java)
                    .putExtra("movement", movement.toBundle())
            )
        }
        binding.gravitySwitch.setOnCheckedChangeListener { _, _ ->
            refresh()
        }

        refresh()
        variablesEt.forEachIndexed { _, editText ->
            editText.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (editText.isFocused) {
                        refresh()
                    }
                }
            })
        }
    }

    private fun refresh() {
        clearError()

        if (!validateCheckBoxCount()) {
            showError(getString(R.string.you_should_set_only_two_variables))
            return
        }

        val newMovement = createMovementFromCheckedValues()
        Calculator.calc(newMovement)

        if (!newMovement.isInvalid()) {
            showError(getString(R.string.cannot_be_solved))
            Log.d("InputActivity", "Not solved: $newMovement")
            return
        }

        Log.d("InputActivity", "Solved")
        movement = newMovement
        resetTextFields()
    }

    private fun clearError() {
        binding.errorTv.text = ""
    }

    private fun showError(message: String) {
        binding.errorTv.text = message
    }

    private fun validateCheckBoxCount(): Boolean {
        return checkBoxes.count { it.isChecked } == 2
    }

    private fun createMovementFromCheckedValues(): Movement {
        val newMovement = Movement(movement.movementType)

        checkBoxes.forEachIndexed { index, checkBox ->
            if (!checkBox.isChecked) return@forEachIndexed

            val value = variablesEt[index].text.toString().toDoubleOrNull() ?: 0.0
            when (index) {
                0 -> newMovement.height0 = value
                1 -> newMovement.Vi =
                    if (movement.movementType == MovementType.V_FALL) -value else value

                2 -> newMovement.Vf = -value
                3 -> newMovement.endTime = value
                4 -> newMovement.maxHeight = value
            }

        }
        newMovement.isGravityRounded = binding.gravitySwitch.isChecked

        return newMovement
    }

    private fun resetTextFields() {
        if (binding.h0Et.text.toString() != movement.height0.toString() && !binding.h0Check.isChecked) {
            binding.h0Et.setText(movement.height0.toString())
        }
        if (binding.viEt.text.toString() != movement.Vi.toString() && !binding.viCheck.isChecked) {
            binding.viEt.setText(movement.Vi.toString())
        }
        if (binding.vfEt.text.toString() != abs(movement.Vf!!).toString() && !binding.vfCheck.isChecked) {
            binding.vfEt.setText(abs(movement.Vf!!).toString())
        }
        if (binding.endTimeEt.text.toString() != movement.endTime.toString() && !binding.endTimeCheck.isChecked) {
            binding.endTimeEt.setText(movement.endTime.toString())
        }
        if (binding.maxHeightEt.text.toString() != movement.maxHeight.toString() && !binding.maxHeightCheck.isChecked) {
            binding.maxHeightEt.setText(movement.maxHeight.toString())
        }
    }
}