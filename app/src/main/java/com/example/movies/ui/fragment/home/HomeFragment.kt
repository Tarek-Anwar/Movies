package com.example.movies.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.fragment.adapter.MainPagingAadapter
import com.example.movies.ui.util.MoviesType
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val mainAdapter by lazy { MainPagingAadapter() }
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        handleResult()
        handelSelectedTab()
       // setupObservers()
        setupListeners()

        handelGetMovies(MoviesType.POPULAR)

    }

    private fun setupListeners() {
        binding.searchViewHome.setOnClickListener { navigateToSearchFragment() }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    viewModel.saveSelectedTabPosition(it.position)
                    setSelectedTab(p0.position)
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }


    private fun setSelectedTab(position: Int) {
        when (position) {
            0 -> handelGetMovies(MoviesType.POPULAR)
            1 -> handelGetMovies(MoviesType.TOP_RATED)
            2 -> handelGetMovies(MoviesType.UPCOMING)
        }

    }

    private fun setupObservers() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.moviesList.collectLatest{ pagingData ->
                mainAdapter.submitData(pagingData)
            }
        }
    }

    private fun handelGetMovies(moviesType: MoviesType){
        viewModel.moviesType.trySend(moviesType)
        setupObservers()

    }

    private fun handelSelectedTab() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedTabPosition.collect { position ->
                binding.tabLayout.getTabAt(position)?.select()
            }
        }
    }
    private fun handleResult() {
        mainAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    binding.progressBarHome.visibility = View.VISIBLE
                    binding.errorTextMain.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    recyclerView.visibility = View.VISIBLE
                    binding.progressBarHome.visibility = View.GONE
                    binding.errorTextMain.visibility = View.GONE
                }
                is LoadState.Error -> {
                    recyclerView.visibility = View.GONE
                    binding.progressBarHome.visibility = View.GONE
                    binding.errorTextMain.visibility = View.VISIBLE
                    binding.errorTextMain.text =
                        (loadState.refresh as LoadState.Error).error.message
                }
            }
        }
    }

    private fun navigateToDetailFragment(id: Int) {
        val action = HomeFragmentDirections.actionMainFragmentToMovieDetailsFragment(id)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun navigateToSearchFragment() {
        val action = HomeFragmentDirections.actionMainFragmentToSearchFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun initRecyclerView() {
        recyclerView = binding.moviesHomeRv
        recyclerView.adapter = mainAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        mainAdapter.onItemClick = {
            navigateToDetailFragment(it.id)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}