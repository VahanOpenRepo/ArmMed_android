package com.armed.am.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Levon Arzumanyan on 09/20/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

abstract class BaseFragment<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) :
    Fragment() {
    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingFactory(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupToolbar()
    }

    fun changeStatusBarColor(@ColorRes color: Int) {
        requireActivity().window?.run {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireActivity(), color)
        }
    }

    abstract fun setupView()
    protected open fun setupToolbar()=Unit
    protected open fun loadData()=Unit
    protected open fun setupObservers()=Unit
    protected open fun setupViewModel() { setupObservers() }

}