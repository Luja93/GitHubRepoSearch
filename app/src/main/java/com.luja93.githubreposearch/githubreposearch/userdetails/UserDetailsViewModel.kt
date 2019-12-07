package com.luja93.githubreposearch.githubreposearch.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luja93.githubreposearch.common.mvvm.BaseViewModel
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.scheduler.SchedulerProvider
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.githubreposearch.repository.user.UserRepo
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    schedulers: SchedulerProvider,
    private val userRepo: UserRepo
) : BaseViewModel(schedulers) {

    private var _user = MutableLiveData<ResourceState<User>>()
    var user: LiveData<ResourceState<User>> = _user

    fun fetchUser(id: Long, username: String) {
        observableCall(_user, {
            userRepo.getUser(id, username)
        })
    }

}