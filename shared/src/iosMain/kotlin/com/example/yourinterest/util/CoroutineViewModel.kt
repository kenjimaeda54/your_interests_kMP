package com.example.yourinterest.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.invoke


actual open class CoroutineViewModel {

    actual val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun  clear() {
        scope.cancel()
    }


}