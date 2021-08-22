package com.faridrjb.whattocook.activities

import androidx.appcompat.app.AppCompatActivity
import com.faridrjb.whattocook.fragments.InitsCategFragment
import com.faridrjb.whattocook.R
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.faridrjb.whattocook.adapters.ViewPagerAdapter
import com.faridrjb.whattocook.databinding.ActivityStorageBinding
import com.faridrjb.whattocook.databinding.DialogFragHelpBinding

class StorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorageBinding

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
        binding = ActivityStorageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener { finish() }
        binding.helpBtn.setOnClickListener { showHelpDialog() }

        // setting up fragments
        adapter = ViewPagerAdapter(supportFragmentManager)
        setUpFragments()
        binding.viewPager.adapter = adapter
        binding.tabL.setupWithViewPager(binding.viewPager)
        setUpIcons()
        //---------------------
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
        val builder = AlertDialog.Builder(this)
        val helpDialogBinding = DialogFragHelpBinding.inflate(layoutInflater)
        val view = helpDialogBinding.root
        val msg = resources.getStringArray(R.array.help_texts)[0]
        helpDialogBinding.helpTV.text = msg
        builder.setView(view)
        builder.create().show()
    }
}