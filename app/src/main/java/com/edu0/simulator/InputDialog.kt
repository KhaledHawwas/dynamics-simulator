package com.edu0.simulator

import android.app.*
import android.content.*
import android.text.*
import com.google.android.material.textfield.*

class InputDialog(private val context: Context) {

    fun show(type: String, onOk: (Double) -> Unit) {
        val editText = TextInputEditText(context).apply {
            inputType =
                InputType.TYPE_CLASS_NUMBER or
                        InputType.TYPE_NUMBER_FLAG_DECIMAL or
                        InputType.TYPE_NUMBER_FLAG_SIGNED
        }
        val dialog = AlertDialog.Builder(context)
            .setTitle("Set $type")
            .setView(editText)
            .setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                val input = editText.text.toString().toDouble()
                onOk(input)
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .create()
        dialog.show()
    }
}