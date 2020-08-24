package com.marcoperini.sliceat.ui.restaurants

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R

class RestaurantsAdapter(private val photoCard: List<CardRestaurantsPhoto>, val resources: Resources) : RecyclerView.Adapter<RestaurantsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.uploaded_photos_item, parent, false)
        return RestaurantsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photoCard.size
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        return holder.bind(photoCard[position], resources)
    }
}

class RestaurantsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardImage: ImageView = itemView.findViewById(R.id.cardview_restaurant_photo)

    fun bind(photo: CardRestaurantsPhoto, resources: Resources) {
        cardImage.setImageDrawable(resources.getDrawable(photo.imageRestaurants))
    }
}
