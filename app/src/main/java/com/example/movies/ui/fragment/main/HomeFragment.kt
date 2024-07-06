package com.example.movies.ui.fragment.main

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
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val mainAdapter by lazy { MainPagingAadapter() }
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding!!.moviesHomeRv
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        moviesObserver()
        searchClickListener()
        tabClickListener()
        handelSelectedTab()
        handleResult()

    }

    private fun tabClickListener() {
        binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    when (p0?.position) {
                        0 -> handelGetMovies("popular")
                        1 -> handelGetMovies("top_rated")
                        2 -> handelGetMovies("now_playing")
                        3 -> handelGetMovies("upcoming")
                    }
                    viewModel.saveSelectedTabPosition(it.position)

                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun moviesObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesList.collectLatest { pagingData ->
                mainAdapter.submitData(pagingData)
            }
        }
    }

    private fun handelGetMovies(typeMovie: String) {
        viewModel.moviesType.trySend(typeMovie)
        //  moviesObserver(typeMovie)
    }

    private fun handelSelectedTab() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedTabPosition.collect { position ->
                binding!!.tabLayout.getTabAt(position)?.select()
            }
        }
    }

    private fun handleResult() {
        mainAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    binding!!.progressBarHome.visibility = View.VISIBLE
                    binding!!.errorTextMain.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    binding!!.progressBarHome.visibility = View.GONE
                    binding!!.errorTextMain.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }

                is LoadState.Error -> {
                    recyclerView.visibility = View.GONE
                    binding!!.progressBarHome.visibility = View.GONE
                    binding!!.errorTextMain.visibility = View.VISIBLE
                    binding!!.errorTextMain.text =
                        (loadState.refresh as LoadState.Error).error.message
                }
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
        binding!!.searchViewHome.setOnClickListener { navigateToSearchFragment() }
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
        recyclerView.adapter = mainAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        mainAdapter.onItemClick = {
            navigateToDetailFragment(it.id)
        }
    }


}