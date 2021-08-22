package com.faridrjb.whattocook

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.Navigation
import com.faridrjb.whattocook.activities.MainActivity
import com.faridrjb.whattocook.databinding.FragmentIntroBinding
import com.faridrjb.whattocook.fragments.SlideFragment
import java.util.*

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titles = ArrayList(listOf(*resources.getStringArray(R.array.introTitles)))
        descriptions =
            ArrayList(listOf(*resources.getStringArray(R.array.introDescriptions)))
        adapter = ViewPagerAdapter(
            childFragmentManager
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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