package com.marcoperini.sliceat.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import kotlinx.android.synthetic.main.item_page.view.container
import kotlinx.android.synthetic.main.item_page.view.ivImage
import kotlinx.android.synthetic.main.item_page.view.tvAbout
import kotlinx.android.synthetic.main.item_page.view.tvTitle
import kotlinx.android.synthetic.main.onboarding_container.view.skipButton

private const val ONBOARDING_SCREEN_SIZE = 3

class OnboardingAdapter(val context: Context) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))

    //get the size of color array
    override fun getItemCount(): Int = 3

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        if(position == 0){
            tvTitle.text = context.getString(R.string.onboardingTitle1)
            tvAbout.text = context.getString(R.string.onboardingText1)
            ivImage.setImageResource(R.drawable.undraw_map)
        }
        if(position == 1) {
            tvTitle.text = context.getString(R.string.onboardingTitle2)
            tvAbout.text = context.getString(R.string.onboardingText2)
            ivImage.setImageResource(R.drawable.undraw_eating_together)
        }
        if(position == 2) {
            tvTitle.text = context.getString(R.string.onboardingTitle3)
            tvAbout.text = context.getString(R.string.onboardingText3)
            ivImage.setImageResource(R.drawable.undraw_eating_together)
        }
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
