package com.haznedar.uploadimage.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.haznedar.uploadimage.R
import com.haznedar.uploadimage.databinding.BottomSheetExitBinding

class SuccessBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetExitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetExitBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvExit.setOnClickListener {

            dismiss()

        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) = SuccessBottomSheet().apply { }
    }

}