package com.faridrjb.whattocook

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridrjb.whattocook.adapters.DashboardSuggRVAdapter
import com.faridrjb.whattocook.databinding.DialogFragHelpBinding
import com.faridrjb.whattocook.databinding.FragmentDashboardBinding
import com.faridrjb.whattocook.fragments.FavoriteFragment
import com.faridrjb.whattocook.fragments.PossibleFragment
import com.google.android.material.animation.AnimationUtils

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.infoBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.actionDashboardToAbout)
        }
        binding.toolbar.helpBtn.setOnClickListener { showHelpDialog() }
        binding.toolbar.dashboardSuggRV.apply {
            adapter =
                DashboardSuggRVAdapter(
                    requireContext(),
                    requireActivity(),
                    listOf("پلو", "خورشت", "کبابی", "پیتزا", "آش و سوپ"),
                    listOf(
                        R.drawable.ic_rice,
                        R.drawable.ic_khoresh,
                        R.drawable.ic_kabab,
                        R.drawable.ic_pizza,
                        R.drawable.ic_soup
                    )
                )
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        }

        // Fragments
        childFragmentManager.beginTransaction().replace(R.id.posFragContainer, PossibleFragment())
            .commit()
        childFragmentManager.beginTransaction().replace(R.id.favFragContainer, FavoriteFragment())
            .commit()
        //------------------

        // fab
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.actionDashboardToStorage)
        }
        binding.dashboardNSV.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if ((scrollY - oldScrollY) > 0) {
                binding.floatingActionButton.translationY = 0F
                binding.floatingActionButton.shrink()
                Handler().postDelayed(
                    { binding.floatingActionButton.animate().translationY(512F) },
                    500
                )
            } else {
                binding.floatingActionButton.animate().translationY(0F)
                Handler().postDelayed(
                    { binding.floatingActionButton.extend() },
                    500
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showHelpDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val helpDialogBinding = DialogFragHelpBinding.inflate(layoutInflater)
        val view = helpDialogBinding.root
        val msg = resources.getStringArray(R.array.help_texts)[1]
        helpDialogBinding.helpTV.text = msg
        builder.setView(view)
        builder.create().show()
    }
}