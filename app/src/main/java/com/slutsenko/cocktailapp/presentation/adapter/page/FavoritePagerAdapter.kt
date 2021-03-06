package com.slutsenko.cocktailapp.presentation.adapter.page

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.slutsenko.cocktailapp.presentation.ui.fragment.FavoriteFragment
import com.slutsenko.cocktailapp.presentation.ui.fragment.HistoryFragment

class FavoritePagerAdapter(fr: Fragment) : FragmentStateAdapter(fr) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HistoryFragment.newInstance()
            else -> FavoriteFragment.newInstance()
        }
    }
}