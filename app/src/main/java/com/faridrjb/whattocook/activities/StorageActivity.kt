package com.faridrjb.whattocook.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.faridrjb.whattocook.fragments.storage.HobFragment
import com.faridrjb.whattocook.fragments.storage.GhalFragment
import com.faridrjb.whattocook.fragments.storage.KhoshkFragment
import com.faridrjb.whattocook.fragments.storage.LabanFragment
import com.faridrjb.whattocook.fragments.storage.MSabziFragment
import com.faridrjb.whattocook.fragments.storage.ChaashFragment
import com.faridrjb.whattocook.fragments.storage.KRoghFragment
import com.faridrjb.whattocook.fragments.storage.ProteinFragment
import com.faridrjb.whattocook.fragments.storage.OtherFragment
import com.faridrjb.whattocook.R
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.faridrjb.whattocook.ViewPagerAdapter
import java.util.*

class StorageActivity : AppCompatActivity() {

    var viewPager: ViewPager? = null
    var tabLayout: TabLayout? = null
    var adapter: ViewPagerAdapter? = null
    var fragments: List<Fragment> = ArrayList(
        Arrays.asList(
            HobFragment(), GhalFragment(), KhoshkFragment(), LabanFragment(), MSabziFragment(),
            ChaashFragment(), KRoghFragment(), ProteinFragment(), OtherFragment()
        )
    )
    var titles = arrayOf(
        "حبوبات", "غلات", "خشکبار", "لبنیات", "میوه و سبزی", "چاشنی ها",
        "کره و روغن", "محصولات پروتئینی", "غیره"
    )
    var icons = intArrayOf(
        R.drawable.ic_beans, R.drawable.ic_wheat, R.drawable.ic_pistachio, R.drawable.ic_cow,
        R.drawable.ic_apple, R.drawable.ic_salt, R.drawable.ic_olive_oil,
        R.drawable.ic_chicken, R.drawable.ic_shopping_cart
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        // back btn
        val back = findViewById<ImageButton>(R.id.icon_back)
        back.setOnClickListener { finish() }
        //---------

        // help btn
        val helpBtn = findViewById<ImageButton>(R.id.helpBtn)
        helpBtn.setOnClickListener { showHelpDialog() }
        //----------

        // setting up fragments
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabs)
        adapter = ViewPagerAdapter(supportFragmentManager)
        setUpFragments()
        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)
        setUpIcons()
        //---------------------
    }

    private fun setUpFragments() {
        for (i in fragments.indices) {
            adapter!!.addFragment(fragments[i], titles[i])
        }
    }

    private fun setUpIcons() {
        for (i in 0 until tabLayout!!.tabCount) {
            tabLayout!!.getTabAt(i)!!.setIcon(icons[i])
        }
    }

    private fun showHelpDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_frag_help, null)
        val helpTV = view.findViewById<TextView>(R.id.helpTV)
        val s = resources.getStringArray(R.array.help_texts)[0]
        helpTV.text = s
        builder.setView(view)
        builder.create().show()
    }
}