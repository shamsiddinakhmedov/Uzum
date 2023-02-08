package com.example.uzum.presentation.view.fragments.cards

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzum.presentation.view.fragments.cards_detail.CardsDetailFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val title: List<String>,
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = title.size

    override fun createFragment(position: Int): Fragment {
        return CardsDetailFragment()
    }
}