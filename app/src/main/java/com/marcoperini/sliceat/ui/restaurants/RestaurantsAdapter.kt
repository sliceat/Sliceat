package com.marcoperini.sliceat.ui.restaurants

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R

class RestaurantsAdapter(private val resources: Resources) : ListAdapter<CardRestaurantsPhoto, RestaurantsPhotoViewHolder>(
    RestaurantsPhotoDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsPhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.uploaded_photos_item, parent, false)
        return RestaurantsPhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantsPhotoViewHolder, listPosition: Int) {
        val restaurantsPhoto = getItem(listPosition)
        holder.bind(restaurantsPhoto)
    }
}

class RestaurantsPhotoDiffCallback : DiffUtil.ItemCallback<CardRestaurantsPhoto>() {
    override fun areItemsTheSame(oldItem: CardRestaurantsPhoto, newItem: CardRestaurantsPhoto): Boolean = oldItem.uid == newItem.uid

    override fun areContentsTheSame(oldItem: CardRestaurantsPhoto, newItem: CardRestaurantsPhoto): Boolean = oldItem == newItem
}

class RestaurantsPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val housePhotoImage: ImageView = itemView.findViewById(R.id.cardview_restaurant_photo)
    fun bind(housePhoto: CardRestaurantsPhoto) {
//        housePhotoImage.loadImageOrRemove(housePhoto.uri.toString())
        housePhotoImage.setImageResource(housePhoto.imageRestaurants)
    }
}
