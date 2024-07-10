package com.example.movies.ui.fragment.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentSearchBinding
import com.example.movies.ui.activities.MainViewModel
import com.example.movies.ui.fragment.adapter.SearchPagingAadapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private var searchJob: Job? = null
    val viewModel: SearchViewModel by viewModels()
    val mainViewModel : MainViewModel by activityViewModels()
    private val searchAdapter by lazy { SearchPagingAadapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        moviesObserver()
        listenerEditText()
        handelBackPressed()

    }

    private fun listenerEditText() {
        binding!!.searchEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    s?.let {
                        fetchMovies(it.toString())
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchMovies(query: String) {
        if (query.isEmpty()) {
            setErrorVisible(getString(R.string.find_your_movie))
        }else{
            lifecycleScope.launch {
                viewModel.keyWordMovies.trySend(query)
            }
        }
    }

    private fun handleResponse() {
        searchAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> setProgressBarVisible()
                is LoadState.NotLoading -> setRecyclerViewVisible()
                is LoadState.Error -> setErrorVisible((loadState.refresh as LoadState.Error).error.message.toString())
            }
        }
    }
    private fun handelBackPressed() {
        binding!!.searchBackImg.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun moviesObserver() {
       lifecycleScope.launch(Dispatchers.IO){
            viewModel.searchResults.collectLatest {
                searchAdapter.submitData(it)
                handleResponse()
            }
        }
    }

    private fun initRecyclerView() {
        binding?.searchMovieRv?.adapter = searchAdapter
        binding?.searchMovieRv?.layoutManager = LinearLayoutManager(requireContext())
        if (searchAdapter.itemCount > 0) setRecyclerViewVisible()
        searchAdapter.onItemClick = {
            navigateToDetailFragment()
            mainViewModel.setMovieDetail(it)
        }
    }
    private fun navigateToDetailFragment() {
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
    private fun setRecyclerViewVisible() {
        binding!!.progressBarSearch.visibility = View.GONE
        binding!!.searchMovieRv.visibility = View.VISIBLE
        binding!!.noSearchLayout.visibility = View.GONE
    }
    private fun setProgressBarVisible() {
        binding!!.progressBarSearch.visibility = View.VISIBLE
        binding!!.searchMovieRv.visibility = View.GONE
        binding!!.noSearchLayout.visibility = View.GONE
    }
    private fun setErrorVisible(error : String){
        binding!!.progressBarSearch.visibility = View.GONE
        binding!!.searchMovieRv.visibility = View.GONE
        binding!!.noSearchLayout.visibility = View.VISIBLE
        binding!!.errorText.text = error
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}