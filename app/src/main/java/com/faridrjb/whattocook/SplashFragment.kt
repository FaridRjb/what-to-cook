package com.faridrjb.whattocook

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import com.faridrjb.whattocook.databinding.FragmentMainBinding
import com.faridrjb.whattocook.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashAnimation()
        Handler().postDelayed({
            if (requireContext().getSharedPreferences("First Time", MODE_PRIVATE).getBoolean("First time", true)) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_splashFragment_to_introFragment)
            } else {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_splashFragment_to_mainFragment)
            }
        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun splashAnimation() {
        val animation: Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.come_up
        )
        binding.splashIV.startAnimation(animation)
    }
}