package com.marcoperini.sliceat.ui.settings

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.android.synthetic.main.item_page.view.ivImage
import kotlinx.android.synthetic.main.item_page_settings.view.title

class SettingsAdapter(val context: Context, val navigator: Navigator) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_page_settings, parent, false))

    //get the size of color array
    override fun getItemCount(): Int = 2

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {

        if (position == 0) {
//            title.text = context.getString(R.string.onboardingTitle1) TODO add correct text and image

            val firstImage = BitmapFactory.decodeResource(resources, R.drawable.view_pager1)

            ivImage.setImageBitmap(firstImage)

            ivImage.setOnClickListener {
                navigator.goToLetterManagersScreen()
            }
        }
        if (position == 1) {
//            title.text = context.getString(R.string.onboardingTitle2) TODO add correct text and image
            val secondImage = BitmapFactory.decodeResource(resources, R.drawable.view_pager1)

            ivImage.setImageBitmap(secondImage)
        }
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
