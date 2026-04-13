package com.example.mybooks.repository

import android.content.Context
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.helper.DatabaseConstants

class BookRepository(context: Context) {


    private var database = BookDatabaseHelper(context)

    private val books = mutableListOf<BookEntity>()



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
    fun getAllBooks(): List<BookEntity>{

        val db = database.readableDatabase
        val books = mutableListOf<BookEntity>()


        // Query me retorna um CURSOR
        val cursor = db.query(DatabaseConstants.BOOK.TABLE_NAME, null,
            null, null, null, null, null)

        if (cursor.moveToFirst()){
            //Logica
            do {
                // dados
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
                val favorite : Boolean = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1


                books.add(BookEntity(id, title, author, favorite, genre))
            }while (cursor.moveToNext())
        }

    // o Cursor é um objeto que representa um conjunto de resultados de uma consulta SQL.
            // Ele e permite navegar no regitros

        // depois vamos passar item a item criando a lista

        //fecho bd / cursor
        cursor.close()
        db.close()

        //returno
        return books
    }

    fun getFavoriteBooks(): List<BookEntity>{

        val db = database.readableDatabase
        val books = mutableListOf<BookEntity>()


        val cursor = db.query(DatabaseConstants.BOOK.TABLE_NAME, null,
            null, null, null, null, null)

        if (cursor.moveToFirst()){

            do {
                // dados
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
                val favorite : Boolean = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1


                books.add(BookEntity(id, title, author, favorite, genre))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return books.filter { it.favorite }
    }

    fun getBookById(id: Int): BookEntity?{
        val db = database.readableDatabase



        val cursor = db.query(DatabaseConstants.BOOK.TABLE_NAME,
            null,
            "${DatabaseConstants.BOOK.COLUMNS.ID} = ?}",
            arrayOf(id.toString()),
            null,
            null,
            null)

        var book: BookEntity? = null


        if (cursor.moveToFirst()){

            // dados
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
            val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
            val favorite : Boolean = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1


           book = BookEntity(id, title, author, favorite, genre)
        }

        cursor.close()
        db.close()

        return book
     }

    fun deleteBook(id: Int): Boolean{
        return books.removeIf { it.id == id }
    }

    fun toggleFavoriteStatus(id: Int){
        val book = books.find { it.id == id }
        if (book != null){
            book.favorite = !book.favorite
        }
    }

}
