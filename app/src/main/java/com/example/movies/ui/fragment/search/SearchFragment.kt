package com.example.movies.ui.fragment.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.data.remote.ApiService
import com.example.movies.databinding.FragmentSearchBinding
import com.example.movies.ui.fragment.adapter.SearchPagingAadapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private var searchJob: Job? = null
    val viewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy { SearchPagingAadapter() }

    @Inject
    lateinit var api: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        listenerEditText()

    }

    private fun listenerEditText() {

        binding!!.searchEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(2000) // Add a debounce delay
                    s?.let { fetchMovies(it.toString()) }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchMovies(query: String) {
        if (query.isEmpty()) {
            handleNoResponse()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.searchMovies(query)
                withContext(Dispatchers.Main) {
                    if (response.movies.isEmpty()) {
                        handleNoResponse()
                    } else {
                        handleResponse()
                        moviesObserver(query)
                    }
                }
            }
        }
    }

    private fun handleNoResponse() {
        binding!!.searchMovieRv.visibility = View.GONE
        binding!!.noSearchLayout.visibility = View.VISIBLE
    }

    private fun handleResponse() {
        binding!!.noSearchLayout.visibility = View.GONE
        binding!!.searchMovieRv.visibility = View.VISIBLE
    }

    private fun moviesObserver(query: String) {
        lifecycleScope.launchWhenStarted {
            viewModel.getMoviesSearch(query).collectLatest { pagingData ->
                searchAdapter.submitData(pagingData)
            }
        }
    }

    private fun initRecyclerView() {
        binding?.searchMovieRv?.adapter = searchAdapter
        binding?.searchMovieRv?.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter.onItemClick = {
            navigateToDetailFragment(it.id)
        }
    }

    private fun navigateToDetailFragment(id: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(id)
        Navigation.findNavController(requireView()).navigate(action)
    }

}