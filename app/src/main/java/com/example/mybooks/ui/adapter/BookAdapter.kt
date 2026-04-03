package com.example.mybooks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.databinding.ItemBookBinding
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.ui.viewholder.BookViewHolder

/**
 * Como o layout se conecta ao codigo extendemos a classe Recycler View
 * essa class precisa de um <VH> ViewHolder criamos a classe BookViewHolder
 */
class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

    private var bookList: List <BookEntity> = listOf()


    /**
     * Responsavel por criar
     */
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): BookViewHolder {
        val view = ItemBookBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        // por que retornar a BookView Holder por que ela é responsavel pelas alterações do layout
        return BookViewHolder(view)
    }

    /**
     * Metodos responsavel em pegar os valores do item e passar para o layout
     * oViewBinding identifica os elementos e nos USAMOS(SABOR) o binding
     * A Holder é um detentor ele mantem a view, ele é resposnsavel por atribuir os valores
     * para os elementos de layout
     */
    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int
    ) {
        holder.bind(bookList[position])
    }

    /**
     * retona quantos elementos existe na recyclerview
     */
    override fun getItemCount(): Int {
        return bookList.size
    }

    fun updateList(list: List<BookEntity>) {
        bookList = list
    }

}