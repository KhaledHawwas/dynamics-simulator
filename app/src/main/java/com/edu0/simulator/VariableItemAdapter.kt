package com.edu0.simulator

import android.content.*
import android.view.*
import androidx.core.content.*
import androidx.recyclerview.widget.*
import com.edu0.simulator.databinding.*
import java.util.*

data class VariableItem(
    val arabicName: String,
    val engName: String,
    var value: Double,
    var isConstant: Boolean = true,
)

class VariableItemAdapter(private val context: Context, private val items: List<VariableItem>):
    RecyclerView.Adapter<VariableItemAdapter.VariableItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariableItemViewHolder {
        val binding =
            VariableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VariableItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VariableItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VariableItemViewHolder(private val binding: VariableItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VariableItem) {
            binding.arTv.text = item.arabicName
            binding.engTv.text = item.engName
            binding.variableTv.text = formatValue(item.value)
            if (item.isConstant) {
                binding.root.backgroundTintList = ContextCompat.getColorStateList(
                    this@VariableItemAdapter.context,
                    R.color.height0
                )
            }
        }
    }

    private fun formatValue(value: Double): String {
        return if (value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            String.format(Locale.US, "%.3f", value)
        }
    }
}