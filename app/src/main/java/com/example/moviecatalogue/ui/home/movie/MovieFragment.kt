package com.example.moviecatalogue.ui.home.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.databinding.FragmentMovieBinding
import com.example.moviecatalogue.ui.home.HomeViewModel
import com.example.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch


class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : HomeViewModel
    private lateinit var mAdapter : MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity(), requireActivity().application)
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            mAdapter = MovieAdapter()

            viewModel.getMovies()
            observe()

            binding.btnSearch.setOnClickListener {
                viewModel.getMovies(binding.edtQuery.text.toString())
                observe()
            }

            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = mAdapter
            }


        }
    }

    private fun observe(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.movie.observe(viewLifecycleOwner, { movies ->
            binding.progressBar.visibility = View.GONE
            lifecycleScope.launch {
                mAdapter.submitData(movies)
            }
        })
    }

}