package com.marcoperini.sliceat.ui.maps

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marcoperini.sliceat.R

class OfflineScreenFragment : BottomSheetDialogFragment() {

    private lateinit var okThanks : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.offline_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        okThanks = view.findViewById(R.id.thanks)
        setupAction()
    }

    private fun setupAction() {
        okThanks.setOnClickListener {
            dismiss()
        }
    }
}
