package com.hello.stageassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hello.stageassignment.R
import com.hello.stageassignment.ui.adapters.StoryViewAdapter
import com.hello.stageassignment.databinding.FragmentFirstBinding
import com.hello.stageassignment.ui.viewmodel.StoryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StoryFragment : Fragment() {

    private val storyViewModel: StoryViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: StoryViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        storyViewModel.stories.onEach { storyList ->
            adapter.submitList(storyList)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupRecyclerView() {
        binding.storyViewRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = StoryViewAdapter { position ->
            val bundle = Bundle().apply {
                putSerializable("STORIES_LIST", ArrayList(adapter.currentList))
            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
        binding.storyViewRV.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
