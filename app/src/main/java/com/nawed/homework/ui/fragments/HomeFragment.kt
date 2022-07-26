package com.nawed.homework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nawed.homework.R
import com.nawed.homework.databinding.FragmentHomeBinding
import com.nawed.homework.ui.adapters.HomePostAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var homePostAdapter: HomePostAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.toolbarLayout.tvToolbar.text = getString(R.string.demo_module)
        binding.toolbarLayout.btnToolbar.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_hamburger))

        // fake data list and set adapter to recyclerview
        val data = arrayListOf("Jhonny Depp", "Amber Heard")
        binding.recyclerViewPostHomeFrag.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL,false)
        homePostAdapter = HomePostAdapter(data)
        binding.recyclerViewPostHomeFrag.adapter = homePostAdapter
        binding.recyclerViewPostHomeFrag.setHasFixedSize(true)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}