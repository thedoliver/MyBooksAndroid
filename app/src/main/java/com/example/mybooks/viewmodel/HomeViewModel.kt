package com.example.mybooks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.repository.BookRepository

class HomeViewModel : ViewModel() {

    //Quando eu chamo a view model ele busca do repositorio reporna o _books e atribui a variavel books
    private val _books = MutableLiveData<List<BookEntity>>()
    //item observdo recebe
    val books: LiveData<List<BookEntity>> = _books

    //Variavel para acessar o repositorios
    private val repository = BookRepository.getInstance()


    fun getAllBooks(){
        _books.value = repository.getAllBooks()

    }

    fun favorite(id: Int){
        repository.toggleFavoriteStatus(id)
    }
}