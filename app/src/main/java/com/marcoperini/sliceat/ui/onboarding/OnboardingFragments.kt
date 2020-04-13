//package com.marcoperini.sliceat.ui.onboarding
//
//import android.graphics.Color
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.core.graphics.drawable.toBitmap
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.marcoperini.sliceat.R
//import kotlinx.android.synthetic.main.onboarding_container.view.skipButton
//
//private const val ARG_ID = "index"
//private const val ARG_TITLE = "text"
//private const val ONBOARDING_SCREEN1 = 0
//private const val ONBOARDING_SCREEN2 = 1
//private const val ONBOARDING_SCREEN3 = 2
//
//class OnboardingFragments : Fragment() {
//    private var screenIndex: Int = 0
//    private var screenText: String = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            screenIndex = it.getInt(ARG_ID, 0)
//            screenText = it.getString(ARG_TITLE, "")
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.onboarding_images, container, false).apply {
//
//            val background = findViewById<ImageView>(R.id.onBoardingImage)
//            when (screenIndex) {
//                ONBOARDING_SCREEN1 -> {
//                    background.setImageDrawable(resources.getDrawable(R.drawable.undraw_map))
//                }
//                ONBOARDING_SCREEN2 -> {
//                    background.setImageDrawable(resources.getDrawable(R.drawable.undraw_eating_together))
//                    }
//                ONBOARDING_SCREEN3 ->
//                    background.setImageDrawable(resources.getDrawable(R.drawable.undraw_map))
//                }
//            findViewById<TextView>(R.id.textOnBoarding).text = screenText
//        }
//    }
//
//    private fun setScreenImage(view: View, background: ImageView?, image1Id: Int, image2Id: Int) {
////        Blurry.with(context).from(resources.getDrawable(R.drawable.bg_volcano).toBitmap()).into(background)
////        view.findViewById<ImageView>(R.id.onboardingImageTop).visibility = View.VISIBLE
////        view.findViewById<ImageView>(R.id.onboardingImageBottom).visibility = View.VISIBLE
////        view.findViewById<ImageView>(R.id.onboardingImageTop).setImageResource(image1Id)
////        view.findViewById<ImageView>(R.id.onboardingImageBottom).setImageResource(image2Id)
//    }
//
////    private fun setUpBillboard(): MutableList<Card> {
////        val billboard = mutableListOf<Card>()
////
////        repeat(NUMBER_CARDS) {
////            billboard.add(Card(CARD_BG_BLACK, CARD_CHARACTER_SEALION, THREE_BIRDS, true))
////        }
////        billboard[CENTER_CARD] = Card(CARD_BG_BLACK, CARD_CHARACTER_SEALION, THREE_BIRDS, false)
////        billboard[HUMAN_SIDE_CARD_3] = Card(CARD_BG_RED, CARD_CHARACTER_TURTLE, CENTER_EMPTY, false)
////
////        return billboard
////    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(screenIndex: Int, screenText: String) =
//            OnboardingFragments().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_ID, screenIndex)
//                    putString(ARG_TITLE, screenText)
//                }
//            }
//    }
//}
