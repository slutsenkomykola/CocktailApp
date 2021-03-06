package com.slutsenko.cocktailapp.data.repository.impl.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.slutsenko.cocktailapp.data.db.source.UserDbSource
import com.slutsenko.cocktailapp.data.network.source.UserNetSource
import com.slutsenko.cocktailapp.data.network.source.UserUploadNetSource
import com.slutsenko.cocktailapp.data.repository.impl.mapper.UserRepoModelMapper
import com.slutsenko.cocktailapp.data.repository.model.UserRepoModel
import com.slutsenko.cocktailapp.data.repository.source.UserRepository
import java.io.File

class UserRepositoryImpl(
        private val userDbSource: UserDbSource,
        private val userNetSource: UserNetSource,
        private val userUploadNetSource: UserUploadNetSource,
        private val userModelMapper: UserRepoModelMapper
) : UserRepository {

    override val userLiveData: LiveData<UserRepoModel?> = userDbSource.userLiveData
        .map {
            when {
                it != null -> userModelMapper.mapDbToRepo(it)
                else -> null
            }
        }

    override suspend fun getUser() = userDbSource.getUser()?.run(userModelMapper::mapDbToRepo)

    override suspend fun refreshUser() {
        userNetSource.getUser()
            .run(userModelMapper::mapNetToDb)
            .run { userDbSource.saveUser(this) }
    }

    override suspend fun updateUser(user: UserRepoModel) {
        userDbSource.saveUser(user.run(userModelMapper::mapRepoToDb))
        userNetSource.updateUser(userModelMapper.mapRepoToNet(user))
    }

    override suspend fun updateUserAvatar(avatar: File, onUploadProgress: (Float) -> Unit): String {
        return userUploadNetSource
            .updateUserAvatar(avatar) { percent, _, _ -> onUploadProgress(percent) }
            .apply{ refreshUser() }
    }

    override suspend fun deleteUser() {
        userDbSource.deleteUser()
    }
}
