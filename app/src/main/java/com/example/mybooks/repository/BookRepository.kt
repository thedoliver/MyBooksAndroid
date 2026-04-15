package com.example.mybooks.repository

import android.content.ContentValues
import android.content.Context
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.helper.DatabaseConstants
import kotlinx.coroutines.flow.Flow

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


    fun getAllBooks(): Flow<List<BookEntity>> {
        return database.getAllBooks()
    }

    fun getFavoriteBooks(): Flow<List<BookEntity>> {
        return database.getFavoriteBooks()
    }

    suspend fun getBookById(id: Int): BookEntity {
        return database.getBookById(id)
    }

    suspend fun deleteBook(id: Int): Boolean {
        return database.delete(getBookById(id)) > 0
    }

    suspend fun toggleFavoriteStatus(id: Int) {
        val book = getBookById(id)
        book.favorite = !book.favorite
        database.update(book)
    }

}
