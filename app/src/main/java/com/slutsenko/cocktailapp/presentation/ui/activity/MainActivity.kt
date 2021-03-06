package com.slutsenko.cocktailapp.presentation.ui.activity

import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.iid.FirebaseInstanceId
import com.slutsenko.cocktailapp.R
import com.slutsenko.cocktailapp.databinding.ActivityMainBinding
import com.slutsenko.cocktailapp.presentation.ui.base.BaseActivity
import com.slutsenko.cocktailapp.presentation.ui.fragment.MainFragment
import com.slutsenko.cocktailapp.presentation.ui.fragment.SettingFragment
import com.slutsenko.cocktailapp.presentation.viewmodel.MainActivityViewModel
import com.slutsenko.cocktailapp.util.FirebaseAnalyticHelper.FirebaseConstant.Companion.MAIN_TAB_NAME
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    override val viewModelClass: KClass<MainActivityViewModel>
        get() = MainActivityViewModel::class
    private var mainFragment: MainFragment? = null
    private var settingFragment: SettingFragment? = null

    override fun myView(): Int {
        return R.layout.activity_main
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }


    override fun activityCreated() {

        val TAG = "taggg"

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    //val msg = getString(1, token)
                    Log.d(TAG, token?:"")

                })



        //var firebase = FirebaseAnalytics.getInstance(this)


        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    viewModel.firebaseAnalytics.logEvent(MAIN_TAB_NAME, bundleOf("main" to "click"))
                    supportFragmentManager
                            .beginTransaction()
                            .hide(settingFragment!!)
                            .show(mainFragment!!)
                            .commit()
                    true
                }
                R.id.menu_setting -> {
                    viewModel.firebaseAnalytics.logEvent(MAIN_TAB_NAME, bundleOf("setting" to "click"))
                    supportFragmentManager
                            .beginTransaction()
                            .hide(mainFragment!!)
                            .show(settingFragment!!)
                            .commit()
                    true
                }
                else -> false
            }
        }

        settingFragment = supportFragmentManager.findFragmentByTag(SettingFragment::class.java.name)
                as? SettingFragment
        mainFragment = supportFragmentManager.findFragmentByTag(MainFragment::class.java.name)
                as? MainFragment
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance()
            fragmentTransaction.add(R.id.fcv_main, settingFragment!!, SettingFragment::class.java.name)
            fragmentTransaction.hide(settingFragment!!)
        }
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance()
            fragmentTransaction.add(R.id.fcv_main, mainFragment!!, MainFragment::class.java.name)
            fragmentTransaction.setPrimaryNavigationFragment(mainFragment!!)
            fragmentTransaction.commit()
        }

        viewModel.showNavigationBarTitlesLiveData.observe(this, Observer {
            if (it) {
                bottom_navigation_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            } else {
                bottom_navigation_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
            }
        })
    }
}