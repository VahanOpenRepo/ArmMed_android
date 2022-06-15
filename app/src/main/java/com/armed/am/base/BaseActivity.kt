package com.armed.am.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by Levon Arzumanyan on 09/20/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) :
    AppCompatActivity() {
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView(savedInstanceState, binding)

    }

    abstract fun setupView(savedInstanceState: Bundle?, binding: B)
    protected open fun setupViewModel() = Unit

}