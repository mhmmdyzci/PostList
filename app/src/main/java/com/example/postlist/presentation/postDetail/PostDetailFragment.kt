package com.example.postlist.presentation.postDetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.postlist.R
import com.bumptech.glide.Glide
import com.example.postlist.databinding.FragmentPostDetailBinding
import com.example.postlist.presentation.base.BaseFragment
import com.example.postlist.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment :
    BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::inflate) {

    private val viewModel: PostDetailViewModel by viewModels()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPostImage()
        setupActions()
        collectUiState()
    }

    private fun loadPostImage() {
        Glide.with(binding.detailPostImage)
            .load(args.imageUrl)
            .into(binding.detailPostImage)
    }

    private fun setupActions() {
        binding.screenHeader.setOnBackClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            viewModel.updatePost(
                title = binding.titleInput.text?.toString().orEmpty(),
                body = binding.bodyInput.text?.toString().orEmpty()
            )
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect{
                        render(it)
                    }
                }
                launch {
                    viewModel.saveCompleted.collect {
                        binding.root.hideKeyboard()
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun render(state: PostDetailUiState) {
        binding.apply {
            loadingView.isVisible = state.isLoading
            detailContent.isVisible = !state.isLoading && state.post != null
            saveButton.isEnabled = !state.isSaving
            saveButton.text = getString(
                if (state.isSaving) R.string.post_detail_saving else R.string.post_detail_save
            )

            state.post?.let { post ->
                if (titleInput.text?.toString() != post.title) {
                    titleInput.setText(post.title)
                }
                if (bodyInput.text?.toString() != post.body) {
                    bodyInput.setText(post.body)
                }
            }

            titleInputLayout.error = when (state.validationError) {
                PostDetailValidationError.EMPTY_TITLE -> getString(R.string.post_detail_title_required)
                else -> null
            }
            bodyInputLayout.error = when (state.validationError) {
                PostDetailValidationError.EMPTY_BODY -> getString(R.string.post_detail_body_required)
                else -> null
            }
            errorMessage.text = state.errorMessage
            errorMessage.isVisible = state.errorMessage != null
        }

    }

}
