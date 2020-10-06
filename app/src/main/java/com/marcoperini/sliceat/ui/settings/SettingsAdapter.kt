package com.marcoperini.sliceat.ui.settings

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.android.synthetic.main.item_page.view.ivImage
import kotlinx.android.synthetic.main.item_page_settings.view.flag
import kotlinx.android.synthetic.main.item_page_settings.view.subtitle
import kotlinx.android.synthetic.main.item_page_settings.view.title
import kotlinx.android.synthetic.main.item_page_settings.view.title2

class SettingsAdapter(val context: Context, val navigator: Navigator, val activity: Activity) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_page_settings, parent, false))

    //get the size of color array
    override fun getItemCount(): Int = 2

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        if (position == 0) {
            title.text = context.getString(R.string.setting_title1)
            subtitle.text = context.getString(R.string.setting_subtitle1)
            title2.visibility = View.GONE

            val firstImage = BitmapFactory.decodeResource(resources, R.drawable.view_pager_one)

            ivImage.setImageBitmap(firstImage)

            ivImage.setOnClickListener {
                navigator.goToLetterManagersScreen()
                activity.finish()
            }
        }
        if (position == 1) {
            title2.text = context.getString(R.string.setting_title2)
            title.visibility = View.GONE
            subtitle.visibility = View.GONE
            flag.visibility = View.GONE

            val secondImage = BitmapFactory.decodeResource(resources, R.drawable.view_pager2)

            ivImage.setImageBitmap(secondImage)

            ivImage.setOnClickListener {
                navigator.goToIntolleranceScreen()
                activity.finish()
            }
        }
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
