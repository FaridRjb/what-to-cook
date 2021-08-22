package com.faridrjb.whattocook.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.faridrjb.whattocook.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
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

        // Next Btn
        val rv = requireActivity().findViewById<ViewPager>(R.id.introSliderVP)
        binding.nextBtn.setOnClickListener {
            if (rv.currentItem + 1 == rv.adapter!!.count) {
                val preferences = requireContext().getSharedPreferences("First Time", AppCompatActivity.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean("First time", false)
                editor.apply()
                Navigation.findNavController(binding.root)
                    .navigate(R.id.actionIntroToMain)
            }
            rv.currentItem = rv.currentItem + 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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