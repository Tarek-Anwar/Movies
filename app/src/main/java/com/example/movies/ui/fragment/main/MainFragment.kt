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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.ui.fragment.adapter.MovieAdapter
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.textSearch.setOnClickListener { navigateToSearchFragment() }
        initRecyclerView()

        lifecycleScope.launch {
            viewModel.moviesState.collect { state ->
                if (state!!.isLoading) {
                    if (state.error == null) {
                     loadingProcess()
                    } else {
                        errorProcess(state.error!!)
                    }
                } else {
                    movieSussessfly()
                    state?.let {
                        adapter.movieModelLocalList = state.moviesList!!
                        adapter.notifyDataSetChanged()

                    }
                }
            }

        }
    }

    private fun loadingProcess(){
        binding!!.progressBar.visibility = View.VISIBLE
        binding!!.rvMovies.visibility = View.GONE
        binding!!.errorTextMain.visibility = View.GONE
    }

    private fun errorProcess(massage: String){
        binding!!.progressBar.visibility = View.GONE
        binding!!.rvMovies.visibility = View.GONE
        binding!!.errorTextMain.visibility = View.VISIBLE
        binding!!.errorTextMain.text = massage
        Log.d(TAG, "onViewCreated: ${massage} ")
    }

    private fun movieSussessfly() {
        binding!!.progressBar.visibility = View.GONE
        binding!!.errorTextMain.visibility = View.GONE
        binding!!.rvMovies.visibility = View.VISIBLE
    }
    private  val TAG = "MainFragment"
    private fun navigateToDetailFragment(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(id)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun navigateToSearchFragment() {
        val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(object : MovieAdapter.MovieItemListener {
            override fun onItemClick(position: Int) {
                navigateToDetailFragment(position)
            }
        })
        binding?.rvMovies?.adapter = adapter
        binding?.rvMovies?.layoutManager = GridLayoutManager(requireContext(), 2)

    }
}