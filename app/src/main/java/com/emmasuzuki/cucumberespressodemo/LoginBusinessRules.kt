package com.emmasuzuki.cucumberespressodemo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class LoginBusinessRules {

    private var tryNumber = 1
    private val maximumNumber = 3
    private val _isUserBlocked = MutableLiveData<Boolean>()
    val isUserBlocked: LiveData<Boolean> get() = _isUserBlocked



    fun increaseTryNumber() = tryNumber++

    fun checkIsUserBlocked(){
        _isUserBlocked.postValue(tryNumber >= maximumNumber)
    }

}
