package com.example.movies.ui.fragment.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.activities.MainViewModel
import com.example.movies.ui.fragment.adapter.MainPagingAadapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val mainAdapter by lazy { MainPagingAadapter() }
    private lateinit var recyclerView: RecyclerView
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupObservers()
        handleResult()
        handelSelectedTab()
        setupListeners()
        handelGetMovies(viewModel.selectedTabPosition.value)
    }

    private fun setupListeners() {
        binding.searchViewHome.setOnClickListener { navigateToSearchFragment() }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    viewModel.saveSelectedTabPosition(it.position)
                    setNullAdapter()
                    handelGetMovies(p0.position)
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun setNullAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainAdapter.submitData(PagingData.empty())
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesList.collectLatest { pagingData ->
                mainAdapter.submitData(pagingData)
            }
        }
    }

    private fun handelGetMovies(position: Int) {
        viewModel.moviesType.trySend(position)
    }

    private fun initRecyclerView() {
        recyclerView = binding.moviesHomeRv
        recyclerView.adapter = mainAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), getNumberSpanCount())
        if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.isComputingLayout) {
            mainAdapter.notifyDataSetChanged()
        }
        mainAdapter.onItemClick = {
            navigateToDetailFragment()
            mainViewModel.setMovieDetail(it)
        }
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
                is LoadState.Loading -> setProgressBarVisible()
                is LoadState.NotLoading -> setRecyclerViewVisible()
                is LoadState.Error -> setErrorVisible((loadState.refresh as LoadState.Error).error.message.toString())
            }
        }
    }

    private fun navigateToDetailFragment() {
        val action = HomeFragmentDirections.actionMainFragmentToMovieDetailsFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun navigateToSearchFragment() {
        val action = HomeFragmentDirections.actionMainFragmentToSearchFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun setRecyclerViewVisible() {
        recyclerView.visibility = View.VISIBLE
        binding.progressBarHome.visibility = View.GONE
        binding.errorTextMain.visibility = View.GONE
    }

    private fun setProgressBarVisible() {
        binding.progressBarHome.visibility = View.VISIBLE
        binding.errorTextMain.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun setErrorVisible(error: String) {
        recyclerView.visibility = View.GONE
        binding.progressBarHome.visibility = View.GONE
        binding.errorTextMain.visibility = View.VISIBLE
        binding.errorTextMain.text = error
    }


    private fun getNumberSpanCount(): Int {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}