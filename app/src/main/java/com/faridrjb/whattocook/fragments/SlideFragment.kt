package com.faridrjb.whattocook.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.faridrjb.whattocook.R
import android.widget.TextView
import androidx.fragment.app.Fragment

class SlideFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_slide, container, false)
        val next = rootView.findViewById<Button>(R.id.btnNext102)
        if (arguments != null) {
            (rootView.findViewById<ImageView>(R.id.logoImage102)).setImageResource(
                requireArguments().getInt("imgResId")
            )
            (rootView.findViewById<TextView>(R.id.tv103)).text =
                requireArguments().getString("title")
            (rootView.findViewById<TextView>(R.id.tv104)).text =
                requireArguments().getString("description")
            if (requireArguments().getString("btnNext") != null) next.text =
                requireArguments().getString("btnNext")
        }
        next.setOnClickListener { v ->
            val activity = activity as CallBacks?
            activity!!.nextClicked(v.id)
        }
        return rootView
    }

    interface CallBacks {
        fun nextClicked(nextBtnId: Int)
    }

    companion object {
        fun newSlide(
            imgResId: Int,
            title: String?,
            description: String?,
            btnNext: String?
        ): SlideFragment {
            val fragment = SlideFragment()
            val args = Bundle()
            args.putInt("imgResId", imgResId)
            args.putString("title", title)
            args.putString("description", description)
            args.putString("btnNext", btnNext)
            fragment.arguments = args
            return fragment
        }
    }
}