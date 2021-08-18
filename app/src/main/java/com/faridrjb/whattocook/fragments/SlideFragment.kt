package com.faridrjb.whattocook.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.faridrjb.whattocook.R
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.faridrjb.whattocook.databinding.FragmentSlideBinding

class SlideFragment : Fragment() {

    private var _binding: FragmentSlideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlideBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            binding.iconIV.setImageResource(
                requireArguments().getInt("imgResId")
            )
            binding.titleTV.text =
                requireArguments().getString("title")
            binding.descTV.text =
                requireArguments().getString("description")
            if (requireArguments().getString("btnNext") != null) binding.nextBtn.text =
                requireArguments().getString("btnNext")
        }
        binding.nextBtn.setOnClickListener { v ->
            val activity = activity as CallBacks?
            activity!!.nextClicked(v.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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