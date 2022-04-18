package com.example.colockumhillsidefarmapp.customer.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is recipe Fragment"
    }
    val text: LiveData<String> = _text
}