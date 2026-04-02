package com.example.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybooks.databinding.FragmentHomeBinding
import com.example.mybooks.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    //A fragment can exist without layout elements
    private var _binding: FragmentHomeBinding? = null



    //garbage collector
    //memory leak

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!! //Will not be null

    private val homeViewModel: HomeViewModel by viewModels() //Delegamos essa inicialização para biblioteca do android x

    //Tres coiss para uma RecicleView nascer
    // 1-Layout
    // 2- idendificação e a atribuição do layout
    // 3 - uma adapater

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







        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}