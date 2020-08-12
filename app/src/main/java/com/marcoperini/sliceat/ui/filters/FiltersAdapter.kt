package com.marcoperini.sliceat.ui.filters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.CardFilter
import com.marcoperini.sliceat.R

class FiltersAdapter(val filterCard: List<CardFilter>, val resources : Resources) : RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_card, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterCard.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(filterCard[position], resources)
    }
}

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardImage: ImageView = itemView.findViewById(R.id.card_filter)
    private val title: TextView = itemView.findViewById(R.id.card_tilte)
    private val checkBox: CheckBox = itemView.findViewById(R.id.first_checkbox)

    fun bind(filterCard: CardFilter, resources: Resources) {
        title.text = resources.getString(filterCard.descriptionCard)
        cardImage.setImageDrawable(resources.getDrawable(filterCard.imageCard))
    }
}
