package com.lfaiska.topredditsreader.presentation.scenes.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.lfaiska.topredditsreader.R
import com.lfaiska.topredditsreader.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    private val splashViewModel: SplashViewModel by viewModel(this, SplashViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        splashViewModel.navigateToNextScene.observe(viewLifecycleOwner,  {
            binding.root.findNavController().navigate(SplashFragmentDirections.navigateToPosts())
        })
    }
}