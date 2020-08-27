package com.marcoperini.sliceat.ui.restaurants

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.filters.CardFilter

class FiltersAdapterRestaurant(private val filterCard: List<CardFilter>, private val resources: Resources) : RecyclerView.Adapter<FiltersRestaurantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersRestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_card_restaurant, parent, false)
        return FiltersRestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterCard.size
    }

    override fun onBindViewHolder(holder: FiltersRestaurantViewHolder, position: Int) {
        return holder.bind(filterCard[position], resources)
    }
}

class FiltersRestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardImage: ImageView = itemView.findViewById(R.id.card_filter_restaurant)
    private val title: TextView = itemView.findViewById(R.id.carta_dei_vini)

    fun bind(filterCard: CardFilter, resources: Resources) {
        title.text = resources.getString(filterCard.descriptionCard)
        cardImage.setImageResource(filterCard.imageCard)
    }
}
