package com.example.postlist.presentation.postList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postlist.databinding.FragmentPostListBinding
import com.example.postlist.presentation.base.BaseFragment
import com.example.postlist.presentation.adapter.PostAdapter
import com.example.postlist.util.navigateWithFade
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostListFragment : BaseFragment<FragmentPostListBinding>(FragmentPostListBinding::inflate) {
    private val viewModel: PostListViewModel by viewModels()
    private val postAdapter = PostAdapter { post ->
        findNavController().navigateWithFade(
            PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(post.id)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupActions()
        collectUiState()
    }

    private fun setupRecyclerView() {
        binding.postRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.postRecyclerView.adapter = postAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return
                viewModel.deletePost(postAdapter.currentList[position])
            }
        }).attachToRecyclerView(binding.postRecyclerView)
    }

    private fun setupActions() {
        binding.retryButton.setOnClickListener { viewModel.refresh() }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect{ postListUiState ->
                    render(postListUiState)
                }
            }
        }
    }

    private fun render(state: PostListUiState) {
        postAdapter.submitList(state.posts)

        binding.apply {
            postRecyclerView.isVisible = state.posts.isNotEmpty()
            loadingView.isVisible = state.isLoading
            errorView.isVisible = state.errorMessage != null && state.posts.isEmpty()
            emptyView.isVisible = !state.isLoading && state.errorMessage == null && state.posts.isEmpty()
            state.errorMessage?.let { errorMessage.text = it }
        }


    }
}
