package com.marcoperini.sliceat.ui.settings

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
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
            title.text = context.getString(R.string.setting_title1)

            val firstImage = BitmapFactory.decodeResource(resources, R.drawable.view_pager1)
            val backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.view_pager1_background)
            val mergedImages = createSingleImageFromMultipleImages(firstImage, backgroundImage)

            ivImage.setImageBitmap(mergedImages)

            ivImage.setOnClickListener {
                navigator.goToLetterManagersScreen()
            }
        }
        if (position == 1) {
            title.text = context.getString(R.string.setting_title2)
            val secondImage = BitmapFactory.decodeResource(resources, R.drawable.view_pager2)
            val backgroundImage2 = BitmapFactory.decodeResource(resources, R.drawable.view_pager2_background)
            val mergedImages2 = createSingleImageFromMultipleImages(secondImage, backgroundImage2)

            ivImage.setImageBitmap(mergedImages2)
        }
    }

    private fun createSingleImageFromMultipleImages(firstImage: Bitmap, secondImage: Bitmap): Bitmap? {
        val result = Bitmap.createBitmap(firstImage.width, firstImage.height, firstImage.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(firstImage, 0f, 0f, null)
        canvas.drawBitmap(secondImage, 0f, 0f, null)
        return result
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
