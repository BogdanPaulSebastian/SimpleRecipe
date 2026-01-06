package net.paulbogdan.simplerecipe.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

    var onError by mutableStateOf("")
    var hasError by mutableStateOf(false)

    var loadingStatus by mutableStateOf(false)
    private set

    var disposeContainer: CompositeDisposable = CompositeDisposable()

    fun notifyIsLoading(){
        loadingStatus = true
    }

    fun notifyFinishedLoading(){
        loadingStatus = false
    }

    fun onDestroy(){
        disposeContainer.dispose()
    }

}