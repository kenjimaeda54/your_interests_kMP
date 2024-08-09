package com.example.yourinterest.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

expect abstract  class CoroutineViewModel() {

   val scope: CoroutineScope

}