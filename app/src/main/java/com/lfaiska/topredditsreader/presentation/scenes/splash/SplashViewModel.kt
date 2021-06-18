package com.lfaiska.topredditsreader.presentation.scenes.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel()  {

    private val _navigateToNextSceneObserver = MutableLiveData<Unit>()
    val navigateToNextScene: LiveData<Unit> get() = _navigateToNextSceneObserver

    init {
        initSplash()
    }

    private fun initSplash() {
        viewModelScope.launch {
            delay(SPLASH_NAVIGATION_DELAY)
            _navigateToNextSceneObserver.postValue(Unit)
        }
    }

    companion object{
        const val SPLASH_NAVIGATION_DELAY = 1000L
    }
}