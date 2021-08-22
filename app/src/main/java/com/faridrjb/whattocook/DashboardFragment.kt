package com.faridrjb.whattocook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.faridrjb.whattocook.databinding.DialogFragHelpBinding
import com.faridrjb.whattocook.databinding.FragmentDashboardBinding
import com.faridrjb.whattocook.fragments.FavoriteFragment
import com.faridrjb.whattocook.fragments.PossibleFragment

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

        binding.floatingButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_dashboardFragment_to_storageFragment)
        }
        binding.inputSearch.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.inputSearch.clearFocus()
                Navigation.findNavController(binding.root).navigate(R.id.action_dashboardFragment_to_searchFragment)
            }
        }
        binding.toolbar.infoBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_dashboardFragment_to_aboutFragment)
        }
        binding.toolbar.helpBtn.setOnClickListener { showHelpDialog() }

        // Fragments
        childFragmentManager.beginTransaction().replace(R.id.posFragContainer, PossibleFragment())
            .commit()
        childFragmentManager.beginTransaction().replace(R.id.favFragContainer, FavoriteFragment())
            .commit()
        //------------------

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