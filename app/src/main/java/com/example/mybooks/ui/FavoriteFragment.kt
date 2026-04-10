package com.example.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybooks.R
import com.example.mybooks.databinding.FragmentFavoriteBinding
import com.example.mybooks.helper.BookConstants
import com.example.mybooks.ui.adapter.BookAdapter
import com.example.mybooks.ui.listener.BookListerner
import com.example.mybooks.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels() //Delegamos essa inicialização para biblioteca do android x
    private val adapter: BookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // removed para viewModels viewMode = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.recyclerviewFavorites.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewFavorites.adapter = adapter

        attachListener()
        setObservers()

        return binding.root
    }

    private fun attachListener(){
        adapter.attachListener(object : BookListerner {
            override fun onClick(id: Int) {

                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID, id)

                findNavController().navigate(R.id.navigation_details, bundle)
            }

            override fun onFavoriteClick(id: Int) {
                favoriteViewModel.favorite(id)
                favoriteViewModel.getFavoriteBooks()
            }
        })
    }

    override fun onResume() {
        //Chamada dos livros
        super.onResume()
        favoriteViewModel.getFavoriteBooks()

    }

    private fun setObservers(){
        favoriteViewModel.books.observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                binding.recyclerviewFavorites.visibility = View.GONE
                binding.textviewNoBooks.visibility = View.VISIBLE
                binding.imageviewNoBooks.visibility = View.VISIBLE
            }else {
                binding.recyclerviewFavorites.visibility = View.VISIBLE
                binding.textviewNoBooks.visibility = View.GONE
                binding.imageviewNoBooks.visibility = View.GONE
                adapter.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}