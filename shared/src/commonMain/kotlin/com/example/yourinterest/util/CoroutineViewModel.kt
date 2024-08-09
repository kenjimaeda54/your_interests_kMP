package com.example.yourinterest.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO



// modifcadores https://medium.com/@HugoMatilla/kotlin-basics-inheritance-modifiers-final-open-abstract-and-override-b1072d728088
expect abstract  class CoroutineViewModel() {

   val scope: CoroutineScope

}