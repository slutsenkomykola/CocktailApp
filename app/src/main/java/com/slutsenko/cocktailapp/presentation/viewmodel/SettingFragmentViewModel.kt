package com.slutsenko.cocktailapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.slutsenko.cocktailapp.data.repository.model.UserRepoModel
import com.slutsenko.cocktailapp.data.repository.source.UserRepository
import com.slutsenko.cocktailapp.presentation.mapper.UserModelMapper
import com.slutsenko.cocktailapp.presentation.model.user.UserModel
import com.slutsenko.cocktailapp.presentation.ui.base.BaseViewModel

class SettingFragmentViewModel(
        application: Application,
        private val userRepository: UserRepository,
        private val userModelMapper: UserModelMapper
) : BaseViewModel(application) {

    var userLiveData: LiveData<UserModel?> = userRepository.userLiveData.map {
        when {
            it != null -> userModelMapper.mapTo(it)
            else -> null
        }
    }

    var firstNameLiveData: MutableLiveData<String?> = MutableLiveData()
    var lastNameLiveData: MutableLiveData<String?> = MutableLiveData()
    var emailLiveData: MutableLiveData<String?> = MutableLiveData()



    fun updateUser() {
        launchRequest {
            userRepository.updateUser(
                    UserRepoModel(userLiveData.value!!.id, firstNameLiveData.value!!, lastNameLiveData.value!!, userLiveData.value!!.email))
        }
    }
}