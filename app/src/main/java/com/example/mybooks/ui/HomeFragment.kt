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
import com.example.mybooks.databinding.FragmentHomeBinding
import com.example.mybooks.helper.BookConstants
import com.example.mybooks.ui.adapter.BookAdapter
import com.example.mybooks.ui.listener.BookListerner
import com.example.mybooks.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    //A fragment can exist without layout elements
    private var _binding: FragmentHomeBinding? = null

    //garbage collector
    //memory leak

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!! //Will not be null

    //Tres coisa para uma RecicleView nascer
    // 1-Layout
    // 2- idendificação e a atribuição do layout
    // 3 - uma adapater
    // 4 - o Adapter chama a ViewHolder
    // 5 - Falta responsabilidade da ViewHolder preecher os valores depois de passar os valores para o
    //o adapter

    //A ViewMode Carraga

    private val homeViewModel: HomeViewModel by viewModels() //Delegamos essa inicialização para biblioteca do android x

    private val adapter: BookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        /**
         *
         */
        binding.recyclerviewBooks.layoutManager = LinearLayoutManager(context)

        /**
         * Eu tenho um elemento de layout o recycler view
         * E tenho um lista de livros
         * Como elas se conectam se conversam
         *o Adapter faz essa funcionalidade
         * Ele dia como a lista vai se "adaptar" / comunicar com o layout
         *
         *
         */
        //Adapter
        binding.recyclerviewBooks.adapter = adapter //adapter precisa receber a lista de livros

        attachListener()

        //Items que vao observar os livros
        setObservers()

        return binding.root
    }

    override fun onResume() {
        //Chamada dos livros
        homeViewModel.getAllBooks()
        super.onResume()
    }

    private fun attachListener(){
        adapter.attachListener(object : BookListerner {
            override fun onClick(id: Int) {

                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID, id)

                findNavController().navigate(R.id.navigation_details, bundle)
            }

            override fun onFavoriteClick(id: Int) {
                homeViewModel.favorite(id)
                homeViewModel.getAllBooks()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers(){
        homeViewModel.books.observe(viewLifecycleOwner) {
            adapter.updateList(it) // it é a lista
        }
    }



}

