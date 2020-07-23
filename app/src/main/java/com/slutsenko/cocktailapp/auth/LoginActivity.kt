package com.slutsenko.cocktailapp.auth

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.slutsenko.cocktailapp.base.BaseActivity
import com.slutsenko.cocktailapp.R
import com.slutsenko.cocktailapp.databinding.ActivityLoginBinding
import com.slutsenko.cocktailapp.ui.activity.MainActivity
import com.slutsenko.cocktailapp.ui.dialog.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    lateinit var login: String
    lateinit var password: String
    private val myLogin = "mykola"
    private val myPassword = "a23456"
    override fun myView(): Int {
        return R.layout.activity_login
    }

    override fun activityCreated() {



//        RegularBottomSheetDialogFragment.newInstance {
//            titleText = "Error"
//            leftButtonText = "Ok"
//            descriptionText = "okay"
//        }.show(supportFragmentManager, RegularBottomSheetDialogFragment::class.java.simpleName)
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                invalidate()
//                et_login.setTextColor(Color.BLACK)
//                et_password.setTextColor(Color.BLACK)
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.loginInputLiveData.value = s.toString()

            }
        }

        val textWatcher2: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.passwordInputLiveData.value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        }
        et_login.addTextChangedListener(textWatcher)
        et_password.addTextChangedListener(textWatcher2)

        viewModel.isLoginDataValidLiveData.observe(this, Observer {
            btn_login.isEnabled = it
        })
    }

    override fun configureDataBinding(binding: ActivityLoginBinding) {
        super.configureDataBinding(binding)
        binding.viewModel = viewModel
    }

    private fun invalidate() {
//        login = et_login.text.toString()
//        password = et_password.text.toString()
//        if (isValidPassword(password) && isValidLogin(login)) {
//            btn_login.isEnabled = true
//            btn_login.setTextColor(Color.WHITE)
//        } else {
//            btn_login.setTextColor(Color.GRAY)
//            btn_login.isEnabled = false
//        }
    }

    private fun isValidLogin(login: String?): Boolean {
        return login?.length!! >= 6
    }


    private fun isValidPassword(password: String?): Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,}$"
            val passwordMatcher = Regex(passwordPattern)
            return passwordMatcher.find(password) != null
        } ?: return false
    }

    fun onClickLogin(v: View?) {
//        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        if (login == myLogin && password == myPassword) {

        startActivity(Intent(this, MainActivity::class.java))
        finish()

        // imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//        } else if (login != myLogin || password != myPassword) {
//            et_login.setTextColor(Color.RED)
//            et_password.setTextColor(Color.RED)
//            if (login != myLogin) {
//                et_login.requestFocus()
//            } else et_password.requestFocus()
//        }
    }

    override fun onStart() {
        invalidate()
        super.onStart()
    }

//    override fun onDialogFragmentClick(
//            dialog: DialogFragment,
//            buttonType: DialogButton,
//            type: DialogType<DialogButton>,
//            data: Any?
//    ) {
//        super.onDialogFragmentClick(dialog, buttonType, type, data)
//        when (type) {
//            RegularDialogType -> {
//                when (buttonType) {
//                    ActionSingleDialogButton -> {
//                        //
//                    }
//                }
//            }
//        }
//    }


}