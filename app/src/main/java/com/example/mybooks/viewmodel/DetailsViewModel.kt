package com.example.mybooks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.repository.BookRepository

class DetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val repository: BookRepository = BookRepository()
    private val _book = MutableLiveData<BookEntity>()
    val book: LiveData<BookEntity> = _book



    fun getBookById(id: Int ){
        _book.value = repository.getBookById(id)
    }

}