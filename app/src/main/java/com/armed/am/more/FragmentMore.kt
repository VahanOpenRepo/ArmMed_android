package com.armed.am.more

import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentMoreBinding
import com.armed.am.utils.logout
import com.armed.am.utils.onClick

class FragmentMore:BaseFragment<FragmentMoreBinding>({ FragmentMoreBinding.inflate(it)}) {
    override fun setupView() {
        binding.logoutBtn.onClick {
            logout()
        }
    }
}