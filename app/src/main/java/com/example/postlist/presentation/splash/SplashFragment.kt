package com.example.postlist.presentation.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import com.example.postlist.R
import androidx.navigation.fragment.findNavController
import com.example.postlist.databinding.FragmentSplashBinding
import com.example.postlist.presentation.base.BaseFragment
import com.example.postlist.util.navigateWithFade

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.splashAnimation.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                navigateToPostListFragment()
            }
        })
    }

    private fun navigateToPostListFragment() {
        val navController = findNavController()
        if (navController.currentDestination?.id != R.id.splashFragment) return

        navController.navigateWithFade(
            SplashFragmentDirections.actionSplashFragmentToPostListFragment(),
            popUpTo = R.id.splashFragment,
            inclusive = true
        )
    }

    override fun onDestroyView() {
        binding.splashAnimation.removeAllAnimatorListeners()
        super.onDestroyView()
    }
}
