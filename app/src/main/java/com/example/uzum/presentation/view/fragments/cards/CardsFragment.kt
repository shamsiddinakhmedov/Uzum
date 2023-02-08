package com.example.uzum.presentation.view.fragments.cards

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.uzum.app.common.Constants.itemsTabLayout
import com.example.uzum.databinding.FragmentCardsBinding
import com.example.uzum.presentation.arcitecture.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : BaseFragment<FragmentCardsBinding>(FragmentCardsBinding::inflate) {
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() = with(binding) {
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, itemsTabLayout)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = itemsTabLayout[position]
        }.attach()

        addIcon.setOnClickListener {
            findNavController().navigate(CardsFragmentDirections.toAddCardFragment())
        }
    }
}