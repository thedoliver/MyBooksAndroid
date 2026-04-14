package com.example.mybooks.repository

import android.content.ContentValues
import android.content.Context
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.helper.DatabaseConstants

class BookRepository(context: Context) {

    private var database = BookDatabase.getDatabase(context).bookDAO()
    //
    companion object {

        private lateinit var instance: BookRepository

        fun getInstance(context: Context): BookRepository {

            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = BookRepository(context)
                }
                return instance
            }
        }

    }

    private fun getInitialBooks(): List<BookEntity> {
        return listOf(
            BookEntity(1, "To Kill a Mockingbird", "Harper Lee", true, "Ficção"),
            BookEntity(2, "Dom Casmurro", "Machado de Assis", false, "Romance"),
            BookEntity(3, "O Hobbit", "J.R.R. Tolkien", true, "Fantasia"),
            BookEntity(4, "Cem Anos de Solidão", "Gabriel García Márquez", false, "Romance"),
            BookEntity(5, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", false, "Fantasia"),
            BookEntity(6, "Crime e Castigo", "Fiódor Dostoiévski", false, "Ficção policial"),
            BookEntity(7, "Frankenstein", "Mary Shelley", false, "Terror"),
            BookEntity(8, "Harry Potter e a Pedra Filosofal", "J.K. Rowling", false, "Fantasia"),
            BookEntity(9, "Neuromancer", "William Gibson", false, "Cyberpunk"),
            BookEntity(10, "Senhor dos Anéis", "J.R.R. Tolkien", false, "Fantasia"),
            BookEntity(11, "Drácula", "Bram Stoker", false, "Terror"),
            BookEntity(12, "Orgulho e Preconceito", "Jane Austen", false, "Romance"),
            BookEntity(13, "Harry Potter e a Câmara Secreta", "J.K. Rowling", false, "Fantasia"),
            BookEntity(14, "As Crônicas de Nárnia", "C.S. Lewis", false, "Fantasia"),
            BookEntity(15, "O Código Da Vinci", "Dan Brown", false, "Mistério"),
            BookEntity(16, "It: A Coisa", "Stephen King", false, "Terror"),
            BookEntity(17, "Moby Dick", "Herman Melville", true, "Aventura"),
            BookEntity(18, "O Nome do Vento", "Patrick Rothfuss", true, "Fantasia"),
            BookEntity(19, "O Conde de Monte Cristo", "Alexandre Dumas", true, "Aventura"),
            BookEntity(20, "Os Miseráveis", "Victor Hugo", false, "Romance")
        )

    }

    fun loadInitialData(){
        val books = getInitialBooks()
        database.insert(books)
    }

    fun getAllBooks(): List<BookEntity> {
        return database.getAllBooks()
    }

    fun getFavoriteBooks(): List<BookEntity> {
        return database.getFavoriteBooks()
    }

    fun getBookById(id: Int): BookEntity {
        return database.getBookById(id)
    }

    fun deleteBook(id: Int): Boolean {
        return database.delete(getBookById(id)) > 0
    }

    fun toggleFavoriteStatus(id: Int) {
        val book = getBookById(id)
        book.favorite = !book.favorite
        database.update(book)
    }

}
