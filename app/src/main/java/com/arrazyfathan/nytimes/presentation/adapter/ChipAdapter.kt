package com.arrazyfathan.nytimes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.nytimes.data.model.Section
import com.arrazyfathan.nytimes.databinding.ItemChipBinding

/**
 * Created by Ar Razy Fathan Rabbani on 01/04/23.
 */
class ChipAdapter(private val sections: List<Section>, private val onSelected: (String) -> Unit) :
    RecyclerView.Adapter<ChipAdapter.ViewHolder>() {

    var selectedItemIndex = -1

    inner class ViewHolder(val binding: ItemChipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section, position: Int) {
            with(binding) {
                sectionChip.text = section.name.replaceFirstChar { it.uppercase() }
                sectionChip.setOnClickListener {
                    if (selectedItemIndex == position) {
                        return@setOnClickListener
                    }

                    if (selectedItemIndex != -1) {
                        sections[selectedItemIndex].isSelected = false
                        notifyItemChanged(selectedItemIndex)
                    }

                    section.isSelected = true
                    selectedItemIndex = position
                    notifyItemChanged(selectedItemIndex)
                    onSelected(section.name)
                }
                sectionChip.isChecked = section.isSelected
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sections[position], position)
    }
}
