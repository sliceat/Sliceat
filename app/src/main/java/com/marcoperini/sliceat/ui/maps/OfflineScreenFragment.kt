package com.marcoperini.sliceat.ui.maps

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marcoperini.sliceat.R

class OfflineScreenFragment : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.offline_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
//        binding.bottomDeleteHouseYesDelete.setOnClickListener {
//            deleteHouseListener.deleteHouseDataPass()
//        }
//
//        binding.bottomDeleteHouseChangedMyMind.setOnClickListener {
//            this.dismiss()
//        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        deleteHouseListener = context as DeleteHouseListener
    }
}

interface DeleteHouseListener {
    fun deleteHouseDataPass()
}
