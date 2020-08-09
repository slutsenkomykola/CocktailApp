package com.slutsenko.cocktailapp.presentation.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.slutsenko.cocktailapp.R
import com.slutsenko.cocktailapp.presentation.adapter.list.CocktailAdapter
import com.slutsenko.cocktailapp.presentation.ui.base.BaseFragment
import com.slutsenko.cocktailapp.presentation.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import kotlin.reflect.KClass

class HistoryFragment : BaseFragment<MainFragmentViewModel>(), CocktailAdapter.OnFavoriteClick {
    override val viewModelClass: KClass<MainFragmentViewModel>
        get() = MainFragmentViewModel::class
    //val viewModel: ParentFragmentViewModel by baseViewModels(requireParentFragment())
    override val contentLayoutResId: Int = R.layout.fragment_history
     var cocktailAdapter: CocktailAdapter? = null

    override fun configureView(savedInstanceState: Bundle?) {
        super.configureView(savedInstanceState)
        viewModel.historyLiveData?.value = viewModel.cocktailDBLiveData?.value
        //cocktailAdapter = CocktailAdapter(requireContext(), viewModel.historyLiveData?.value!!)
        if (viewModel.historyLiveData?.value == null) {
            tv_history.setText(R.string.history)
        } else {
            cocktailAdapter = CocktailAdapter(requireContext(), viewModel.historyLiveData?.value!!)
            rv_database.layoutManager = GridLayoutManager(context, MainFragment.COLUMN)
            rv_database.adapter = cocktailAdapter
            tv_history.text = ""
        }
        cocktailAdapter?.favoriteCallback = this

        viewModel.mediatorLiveData.observe(requireActivity(), Observer {
            viewModel.historyLiveData?.value = it
            cocktailAdapter?.refreshData(viewModel.historyLiveData?.value!!)
            viewModel.cocktailQuantityLiveData.value = it.size
        })

        viewModel.cocktailDBLiveData?.observe(requireActivity(), Observer {
            viewModel.refreshParam()
        })
    }

    companion object {

        fun newInstance(): HistoryFragment {

            return HistoryFragment()
        }
    }

    override fun refreshDB() {
//        viewModel.cocktailDBLiveData?.value =
//                CocktailDatabase.getInstance(requireContext())?.cocktailDao()?.cocktails as List<Cocktail>
    }
}

