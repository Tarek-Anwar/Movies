package com.example.movies.ui.fragment.detalis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.movies.databinding.FragmentMainDetailsBinding

class MovieDetailsFragment : Fragment() {
    private var binding: FragmentMainDetailsBinding? = null

    val args : MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainDetailsBinding.inflate(inflater, container, false)
        val data = arguments?.getString("data")

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated test: id " + args.id)
    }
    private  val TAG = "MovieDetailsFragment"
}