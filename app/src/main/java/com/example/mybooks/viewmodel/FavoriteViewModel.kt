package com.example.mybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.repository.BookRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

     private val repository = BookRepository.getInstance(application.applicationContext)
     val bookList: LiveData<List<BookEntity>> = repository.getFavoriteBooks().asLiveData()



    fun favorite(id: Int) {
        repository.toggleFavoriteStatus(id)

    }


}