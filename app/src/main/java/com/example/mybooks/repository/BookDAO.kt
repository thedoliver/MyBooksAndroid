package com.example.mybooks.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mybooks.entity.BookEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BookDAO {

    /**
     * Entity
     * List<Entity>
     * LiveData<Entity> não use o Live data no repositorio
     * Flow<Entity> ementa para camada acima no caso o repositio
     * Cursor
     * int, Long, Boolean
     */

    @Query("SELECT * FROM Book")
    fun getAllBooks(): Flow<List<BookEntity>>


    @Query("SELECT * FROM Book WHERE favorite = 1")
    fun getFavoriteBooks(): Flow<List<BookEntity>>


    @Query("SELECT * FROM Book WHERE id = :id") //uso de placeholder para passar o id
    suspend fun getBookById(id: Int): BookEntity

    @Update
    suspend fun update(book: BookEntity): Int

    @Delete
    suspend fun delete(book: BookEntity): Int

    @Insert
    suspend fun insert(book: List<BookEntity>): List<Long>
}