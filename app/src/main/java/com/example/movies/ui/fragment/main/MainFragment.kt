package com.example.movies.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.ui.fragment.adapter.MovieAdapter
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movies = viewModel.getMovies2()

        val adapter = MovieAdapter(object : MovieAdapter.MovieItemListener {
            override fun onItemClick(position: Int) {
                navigateToWordFragment(position)
            }
        })
        binding?.rvMovies?.adapter = adapter
        binding?.rvMovies?.layoutManager = GridLayoutManager(requireContext(), 2)

        //  adapter.arrayList = viewModel.getMovies()

        lifecycleScope.launch {
            viewModel.moviesState.collect { movies ->
                movies?.let {
                    Log.d(TAG, "onViewCreated test: " + it.movies.size)
                    Log.d(TAG, "onViewCreated test: " + it.movies.get(1).posterPath)
                    adapter.arrayList = movies.movies
                    adapter.notifyDataSetChanged()
                }
            }
        }

    }
    private fun navigateToWordFragment(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(id.toString())
        Navigation.findNavController(requireView()).navigate(action)

    }

    private val TAG = "MainFragment"
}