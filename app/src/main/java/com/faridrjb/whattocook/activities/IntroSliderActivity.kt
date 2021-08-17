package com.faridrjb.whattocook.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.faridrjb.whattocook.R
import android.os.Bundle
import com.faridrjb.whattocook.fragments.SlideFragment
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.faridrjb.whattocook.databinding.ActivityIntroSliderBinding
import java.util.*

class IntroSliderActivity : AppCompatActivity(), SlideFragment.CallBacks {

    private lateinit var binding: ActivityIntroSliderBinding

    var adapter: ViewPagerAdapter? = null
    private val imgResIds = intArrayOf(
        R.mipmap.ic_launcher,
        R.drawable.ic_kitchen,
        R.drawable.ic_favorite_filled
    )
    private var titles: List<String> = ArrayList()
    private var descriptions: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroSliderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        titles = ArrayList(Arrays.asList(*resources.getStringArray(R.array.introTitles)))
        descriptions =
            ArrayList(Arrays.asList(*resources.getStringArray(R.array.introDescriptions)))
        adapter = ViewPagerAdapter(
            supportFragmentManager
        )

        // Add fragments
        for (i in titles.indices) {
            if (i == titles.size - 1) adapter!!.addFragment(
                SlideFragment.newSlide(
                    imgResIds[i],
                    titles[i],
                    descriptions[i],
                    "شروع کن"
                )
            ) else adapter!!.addFragment(
                SlideFragment.newSlide(
                    imgResIds[i], titles[i], descriptions[i], null
                )
            )
        }
        //---------------
        binding.introSliderVP.adapter = adapter
    }

    override fun nextClicked(nextBtnId: Int) {
        if (binding.introSliderVP.currentItem + 1 == adapter!!.count) {
            val preferences = getSharedPreferences("First Time", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean("First time", false)
            editor.apply()
            startActivity(Intent(this@IntroSliderActivity, MainActivity::class.java))
            finish()
        }
        binding.introSliderVP.currentItem = binding.introSliderVP.currentItem + 1
    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragmentList: MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }
}