package com.example.mybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.repository.BookRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    //Variavel para acessar o repositorios
    private val repository = BookRepository.getInstance(application.applicationContext)

    //Quando eu chamo a view model ele busca do repositorio reporna o _books e atribui a variavel books
    private val _books = MutableLiveData<List<BookEntity>>()
    //item observdo recebe
    val books: LiveData<List<BookEntity>> = _books




    fun getAllBooks(){
        _books.value = repository.getAllBooks()

    }

    fun favorite(id: Int){
        repository.toggleFavoriteStatus(id)
    }
}