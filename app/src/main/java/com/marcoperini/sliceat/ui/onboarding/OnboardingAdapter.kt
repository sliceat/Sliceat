package com.marcoperini.sliceat.ui.onboarding

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import kotlinx.android.synthetic.main.item_page.view.ivImage
import kotlinx.android.synthetic.main.item_page.view.tvAbout
import kotlinx.android.synthetic.main.item_page.view.tvTitle

class OnboardingAdapter(val context: Context) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))

    //get the size of color array
    override fun getItemCount(): Int = 3

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        val backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.onboarding_background)

        if (position == 0) {
            tvTitle.text = context.getString(R.string.onboardingTitle1)
            tvAbout.text = context.getString(R.string.onboardingText1)

            val firstImage = BitmapFactory.decodeResource(resources, R.drawable.onboarding_first)
            val mergedImages = createSingleImageFromMultipleImages(firstImage, backgroundImage)

            ivImage.setImageBitmap(mergedImages)
        }
        if (position == 1) {
            tvTitle.text = context.getString(R.string.onboardingTitle2)
            tvAbout.text = context.getString(R.string.onboardingText2)
            val firstImage = BitmapFactory.decodeResource(resources, R.drawable.onboarding_second)
            val mergedImages = createSingleImageFromMultipleImages(firstImage, backgroundImage)

            ivImage.setImageBitmap(mergedImages)
        }
        if (position == 2) {
            tvTitle.text = context.getString(R.string.onboardingTitle3)
            tvAbout.text = context.getString(R.string.onboardingText3)
            val firstImage = BitmapFactory.decodeResource(resources, R.drawable.onboarding_third)
            val mergedImages = createSingleImageFromMultipleImages(firstImage, backgroundImage)

            ivImage.setImageBitmap(mergedImages)
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
