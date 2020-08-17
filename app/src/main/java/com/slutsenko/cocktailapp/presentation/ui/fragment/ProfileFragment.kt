package com.slutsenko.cocktailapp.presentation.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.slutsenko.cocktailapp.R
import com.slutsenko.cocktailapp.auth.LoginActivity
import com.slutsenko.cocktailapp.presentation.ui.activity.SplashActivity
import com.slutsenko.cocktailapp.presentation.ui.base.BaseFragment
import com.slutsenko.cocktailapp.presentation.ui.dialog.*
import com.slutsenko.cocktailapp.presentation.viewmodel.MainFragmentViewModel
import com.slutsenko.cocktailapp.util.Language
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlin.reflect.KClass

class ProfileFragment : BaseFragment<MainFragmentViewModel>() {
    override val viewModelClass: KClass<MainFragmentViewModel>
        get() = MainFragmentViewModel::class
    override val contentLayoutResId: Int = R.layout.fragment_profile
    //private val mainActivityViewModel: MainActivityViewModel by baseViewModels()
    private lateinit var bottomSheetDialogFragment: RegularBottomSheetDialogFragment
    private lateinit var languageListBottomSheetDialogFragment: LanguageListBottomSheetDialogFragment

    override fun configureView(savedInstanceState: Bundle?) {
        super.configureView(savedInstanceState)

        chb_showTitle.setOnCheckedChangeListener { _, isChecked ->
           // mainActivityViewModel.showNavigationBarTitlesLiveData.value = isChecked
        }

        btn_logOut.setOnClickListener {
            bottomSheetDialogFragment = RegularBottomSheetDialogFragment.newInstance {
                titleText = getString(R.string.logout)
                descriptionText = getString(R.string.logout_desc)
                leftButtonText = getString(R.string.cancel)
                rightButtonText = getString(R.string.accept)
            }
            bottomSheetDialogFragment.show(childFragmentManager, RegularBottomSheetDialogFragment::class.java.simpleName)
        }

        btn_changeLang.setOnClickListener {
            languageListBottomSheetDialogFragment = LanguageListBottomSheetDialogFragment.newInstance(
                    Language.ENGLISH)
            languageListBottomSheetDialogFragment.show(childFragmentManager, LanguageListBottomSheetDialogFragment::class.java.simpleName)
        }
    }


    override fun onBottomSheetDialogFragmentClick(
            dialog: DialogFragment,
            buttonType: DialogButton,
            type: DialogType<DialogButton>,
            data: Any?
    ) {
        super.onBottomSheetDialogFragmentClick(dialog, buttonType, type, data)
        when (type) {
            RegularDialogType -> {
                when (buttonType) {
                    RightDialogButton -> {
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        requireContext().startActivity(intent)
                        requireActivity().finishAffinity()
                    }
                    LeftDialogButton -> {
                        dialog.dismiss()
                    }
                }
            }
            LanguageDialogType -> {
                when (buttonType) {
                    ItemListDialogButton -> {
                        val selectedLanguage = data as Language
                        val sharedPref = activity?.getSharedPreferences("lang", Context.MODE_PRIVATE) ?: return
                        sharedPref.edit().putString("language", selectedLanguage.locale).apply()
                        val intent = Intent(requireContext(), SplashActivity::class.java)
                        requireActivity().startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}