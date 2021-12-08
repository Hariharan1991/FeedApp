package com.example.newsfeedapp.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageSwipeAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private final var fragmentList1: ArrayList<Fragment> = ArrayList()
    private final var fragmentTitleList1: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList1.get(position)
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList1.get(position)
    }

    override fun getCount(): Int {
        return fragmentList1.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList1.add(fragment)
        fragmentTitleList1.add(title)
    }
}