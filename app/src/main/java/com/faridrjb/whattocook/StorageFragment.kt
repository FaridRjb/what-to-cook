package com.faridrjb.whattocook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.faridrjb.whattocook.adapters.ViewPagerAdapter
import com.faridrjb.whattocook.databinding.DialogFragHelpBinding
import com.faridrjb.whattocook.databinding.FragmentStorageBinding
import com.faridrjb.whattocook.fragments.InitsCategFragment

class StorageFragment : Fragment() {

    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!

    var adapter: ViewPagerAdapter? = null
    var itemsArrayID: List<Int> = listOf(
        R.array.hoboobaat_names,
        R.array.ghallaat_names,
        R.array.khoshkbar_names,
        R.array.labaniaat_names,
        R.array.miveh_sabzi_names,
        R.array.chaashni_names,
        R.array.kareh_roghan_names,
        R.array.protein_names,
        R.array.others_names
    )
    var titles = arrayOf(
        "حبوبات", "غلات", "خشکبار", "لبنیات", "میوه و سبزی", "چاشنی ها",
        "کره و روغن", "محصولات پروتئینی", "غیره"
    )
    var icons = intArrayOf(
        R.drawable.ic_beans,
        R.drawable.ic_wheat,
        R.drawable.ic_pistachio,
        R.drawable.ic_cow,
        R.drawable.ic_apple,
        R.drawable.ic_salt,
        R.drawable.ic_olive_oil,
        R.drawable.ic_chicken,
        R.drawable.ic_shopping_cart
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        binding.helpBtn.setOnClickListener { showHelpDialog() }

        // setting up fragments
        adapter = ViewPagerAdapter(childFragmentManager)
        setUpFragments()
        binding.viewPager.adapter = adapter
        binding.tabL.setupWithViewPager(binding.viewPager)
        setUpIcons()
        //---------------------
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpFragments() {
        for (i in itemsArrayID.indices) {
            adapter!!.addFragment(InitsCategFragment(itemsArrayID[i]), titles[i])
        }
    }

    private fun setUpIcons() {
        for (i in 0 until binding.tabL.tabCount) {
            binding.tabL.getTabAt(i)!!.setIcon(icons[i])
        }
    }

    private fun showHelpDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val helpDialogBinding = DialogFragHelpBinding.inflate(layoutInflater)
        val view = helpDialogBinding.root
        val msg = resources.getStringArray(R.array.help_texts)[0]
        helpDialogBinding.helpTV.text = msg
        builder.setView(view)
        builder.create().show()
    }
}