package com.example.yourinterest.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel

actual  open class CoroutineViewModel {

    actual val scope = CoroutineScope(Dispatchers.IO)

    fun  clear() {
        scope.cancel()
    }


}