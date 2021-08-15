package com.faridrjb.whattocook

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titles: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titles.add(title)
    }
}