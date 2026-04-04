package com.example.mybooks.ui.viewholder
/*
Reclycler View é uma item de layout que consegue usar uma
lista de informação e imprimir essa lista

1 - Layout

 */
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.R
import com.example.mybooks.databinding.ItemBookBinding
import com.example.mybooks.entity.BookEntity

/**
 *  E tambem extende da recycle view holder que precisa de uma View
 *  criamos um item layout xml um elemento de layaout
 *  adicionamos a view holder para resolver a cadeia de dependencias
 *  como temos o layout e temos o ViewBinding ele olha o layout e nasce uma classe binding
 *  para passar uma item para a classe que estamos herdando tempos que ter o item na classe de origem
 *
 */
class BookViewHolder(private val item: ItemBookBinding): RecyclerView.ViewHolder(item.root) {

    fun bind(book: BookEntity ) {
        // Agora temos que atribuir os elementos de layout
        item.textviewTitle.text = book.title
        item.textviewAuthor.text = book.author
        item.textviewGenre.text = book.genre

        setGenreBackground(book.genre)
        updateFavoriteIcon(book.favorite)



    }

    private fun setGenreBackground(genre: String){
        when (genre) {
            "Terror" -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_red)
            }
            "Fantasia" -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_fantasy)
            }
            else -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_teal)
            }
        }
    }

    private fun updateFavoriteIcon(favorite: Boolean){
        if(favorite){
            item.imageviewFavorite.setImageResource(R.drawable.ic_favorite)
        }else {
            item.imageviewFavorite.setImageResource(R.drawable.ic_favorite_empty)
        }
    }

}


