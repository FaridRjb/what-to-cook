package com.faridrjb.whattocook.fragments.introslider

import com.faridrjb.whattocook.fragments.introslider.SlideLogoFragment.CallBacks
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.faridrjb.whattocook.R

class SlideLogoFragment : Fragment() {
    private var activity: CallBacks? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as CallBacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_logo_slide, container, false)
        val next = rootView.findViewById<Button>(R.id.btnNext101)
        next.setOnClickListener { v -> activity?.nextClicked(v.id) }
        return rootView
    }

    interface CallBacks {
        fun nextClicked(nextBtnId: Int)
    }
}