package com.marcoperini.sliceat.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val ONBOARDING_SCREEN_SIZE = 4

class OnboardingAdapter(fm: FragmentManager, private val stringArray: Array<String>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OnboardingFragments.newInstance(
            position,
            stringArray[position]
        )
    }

    override fun getCount(): Int {
        return ONBOARDING_SCREEN_SIZE
    }
}
