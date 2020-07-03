package com.slutsenko.cocktailapp.auth

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.slutsenko.cocktailapp.base.BaseActivity
import com.slutsenko.cocktailapp.R
import com.slutsenko.cocktailapp.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    val EXTRA_KEY_LOGIN = "LOGIN"
    val EXTRA_KEY_PASSWORD = "PASSWORD"

    lateinit var login: String
    lateinit var password: String
    private val myLogin = "mykola"
    private val myPassword = "a23456"
    override fun myView(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        et_login.text = savedInstanceState?.getString(EXTRA_KEY_LOGIN)
//        et_password.text = savedInstanceState.getString(EXTRA_KEY_PASSWORD)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putString(EXTRA_KEY_LOGIN, et_login)
//        outState.putString(EXTRA_KEY_PASSWORD, et_password)
    }

    override fun activityCreated() {
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

}