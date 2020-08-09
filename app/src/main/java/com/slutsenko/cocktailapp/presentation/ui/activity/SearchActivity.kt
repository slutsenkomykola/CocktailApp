package com.slutsenko.cocktailapp.presentation.ui.activity

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.slutsenko.cocktailapp.R
import com.slutsenko.cocktailapp.data.network.CocktailList
import com.slutsenko.cocktailapp.data.network.JsonPlaceholderApi
import com.slutsenko.cocktailapp.databinding.ActivitySearchBinding
import com.slutsenko.cocktailapp.presentation.adapter.list.CocktailNetAdapter
import com.slutsenko.cocktailapp.presentation.ui.base.BaseActivity
import com.slutsenko.cocktailapp.presentation.ui.fragment.MainFragment.Companion.COLUMN
import com.slutsenko.cocktailapp.presentation.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass


class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>() {

    override val viewModelClass: KClass<SearchViewModel>
        get() = SearchViewModel::class
   // override val viewModel: SearchViewModel by viewModels()
    var cocktailAdapter: CocktailNetAdapter? = null
    private lateinit var jsonPlaceholderApi : JsonPlaceholderApi

    override fun myView(): Int {
        return R.layout.activity_search
    }

    override fun activityCreated() {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
         jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)


        viewModel.answerLiveData?.value = getString(R.string.enter_text)
        rv_search.layoutManager = GridLayoutManager(this, COLUMN)
////        tiet_text.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
////            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
////                search()
////                true
////            } else {
////                false
////            }
////        }
////        til_search.setStartIconOnClickListener { v: View? -> search() }
//
//
        viewModel.searchLiveData?.observe(this, Observer {
            search()
        })
    }

    override fun configureDataBinding(binding: ActivitySearchBinding) {
        super.configureDataBinding(binding)
        binding.viewModel = viewModel
    }


    private fun search() {


        if (viewModel.searchLiveData?.value.isNullOrEmpty()) {
            viewModel.answerLiveData?.value = getString(R.string.enter_text)
            rv_search.adapter = null
        } else {
            val call = jsonPlaceholderApi.getPosts(viewModel.searchLiveData?.value)
            call?.enqueue(object : Callback<CocktailList?> {
                override fun onResponse(call: Call<CocktailList?>, response: Response<CocktailList?>) {
                    val cocktail = response.body()?.cocktails
                    if (cocktail != null) {
                        cocktailAdapter = CocktailNetAdapter(this@SearchActivity, cocktail)
                        rv_search.adapter = cocktailAdapter
                        viewModel.answerLiveData?.value = ""
                    } else {
                        viewModel.answerLiveData?.value = getString(R.string.no_found)
                        rv_search.adapter = null
                    }
                }

                override fun onFailure(call: Call<CocktailList?>, t: Throwable) {
                    viewModel.answerLiveData?.value = t.message
                }
            })
        }
    }

    companion object {
        private const val URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    }

    //override val viewModel: SearchViewModel by viewModels()
}