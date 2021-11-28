package com.example.moviecatalogue.ui.home.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.databinding.FragmentTvShowBinding
import com.example.moviecatalogue.ui.home.HomeViewModel
import com.example.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch


class TvShowFragment : Fragment() {

    private var _binding : FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : HomeViewModel
    private lateinit var tvShowAdapter : TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity(), requireActivity().application)
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            tvShowAdapter = TvShowAdapter()

            viewModel.getTvShow()
            observe()

            binding.btnSearch.setOnClickListener {
                viewModel.getTvShow(binding.edtQuery.text.toString())
                observe()
            }

            with(binding.rvTvShow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }

    private fun observe() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.tv.observe(viewLifecycleOwner, { tvShows ->
            binding.progressBar.visibility = View.GONE
            lifecycleScope.launch {
                tvShowAdapter.submitData(tvShows)
            }
        })
    }

}