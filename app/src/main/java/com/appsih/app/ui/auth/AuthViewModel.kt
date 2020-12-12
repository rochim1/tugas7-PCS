package com.appsih.app.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsih.app.data.model.ActionState
import com.appsih.app.data.model.AuthUser
import com.appsih.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(val repo: AuthRepository): ViewModel() {
    val authUser = repo.authUser
    val islogin = repo.islogin
    val authLogin = MutableLiveData<ActionState<AuthUser>>()
    val authRegister = MutableLiveData<ActionState<AuthUser>>()
    val authLogout = MutableLiveData<ActionState<Boolean>>()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()

    fun login() {
        viewModelScope.launch {
            val resp = repo.login(email.value ?:"", password.value ?: "")
            authLogin.value = resp
        }
    }

    fun register(){
        viewModelScope.launch {
            val resp = repo.register(
                AuthUser(
                    email = email.value ?: "",
                        password = password.value ?: "",
                        fullName = fullName.value ?: ""
                )
            )
            authRegister.value = resp
        }
    }

    fun logout(){
        viewModelScope.launch {
            val resp = repo .logout()
            authLogout.value = resp
        }
    }
}