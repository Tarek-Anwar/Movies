package com.example.movies.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.ui.fragment.adapter.MainPagingAadapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private val mainAdapter by lazy { MainPagingAadapter() }
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        recyclerView = binding!!.rvMovies
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        moviesObserver()
        searchClickListener()
        tabClickListener()
        handelSelectedTab()
       // handelRecyclerView()
    }

    private fun tabClickListener() {
        binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    viewModel.saveSelectedTabPosition(it.position)
                }
                when (p0?.position) {
                    0 -> handelGetMovies("popular")
                    1 -> handelGetMovies("top_rated")
                    2 -> handelGetMovies("now_playing")
                    3 -> handelGetMovies("upcoming")
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun moviesObserver(custom: String = "popular") {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCustomMovies(custom).collectLatest { pagingData ->
                mainAdapter.submitData(pagingData)
                /*viewModel.pagingDataFlow.value = pagingData
                viewModel.recyclerViewState?.let { state ->
                    recyclerView.layoutManager?.onRestoreInstanceState(state)
                }*/
            }
        }

    }

    private fun handelGetMovies(typeMovie: String) {
        viewModel.getCustomMovies(typeMovie)
        moviesObserver(typeMovie)
    }

    private fun handelSelectedTab() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedTabPosition.collect { position ->
                binding!!.tabLayout.getTabAt(position)?.select()
            }
        }
    }

  /*  private fun handelRecyclerView() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                }
            }
        })
    }*/

    private fun searchClickListener() {
        binding!!.textSearch.setOnClickListener { navigateToSearchFragment() }
    }


    private fun navigateToDetailFragment(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(id)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun navigateToSearchFragment() {
        val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun initRecyclerView() {
        binding?.rvMovies?.adapter = mainAdapter
        binding?.rvMovies?.layoutManager = GridLayoutManager(requireContext(), 2)
        mainAdapter.onItemClick = {
            navigateToDetailFragment(it.id)
        }
    }

  /*  override fun onPause() {
        super.onPause()
        // Save RecyclerView state when the fragment is paused
        viewModel.recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
    }*/
}