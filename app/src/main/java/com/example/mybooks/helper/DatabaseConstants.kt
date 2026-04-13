package com.example.mybooks.helper

class DatabaseConstants private constructor(){
    object BOOK {
        const val TABLE_NAME = "Books"

        object COLUMNS {
            const val ID = "id"
            const val TITLE = "Title"
            const val AUTHOR = "Author"
            const val GENRE = "Genre"
            const val FAVORITE = "Favorite"
        }

    }

}