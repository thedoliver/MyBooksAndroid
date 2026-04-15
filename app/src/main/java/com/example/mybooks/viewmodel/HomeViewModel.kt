package com.example.mybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.repository.BookRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    //Variavel para acessar o repositorios
    private val repository = BookRepository.getInstance(application.applicationContext)


    //Quando eu chamo a view model ele busca do repositorio reporna o _books e atribui a variavel books

    //item observdo recebe
    val bookList: LiveData<List<BookEntity>> = repository.getAllBooks().asLiveData()


    fun favorite(id: Int) {
        repository.toggleFavoriteStatus(id)
    }
}