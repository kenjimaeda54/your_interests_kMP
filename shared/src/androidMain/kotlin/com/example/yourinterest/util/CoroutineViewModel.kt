package com.example.yourinterest.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.viewModelScope


actual  open class CoroutineViewModel: ViewModel() {

    actual val scope: CoroutineScope = viewModelScope


}