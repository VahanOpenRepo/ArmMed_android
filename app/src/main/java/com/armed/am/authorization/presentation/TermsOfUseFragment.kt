package com.armed.am.authorization.presentation

import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentTermsOfUseBinding
import com.armed.am.utils.hide
import com.armed.am.utils.navigateUp
import com.armed.am.utils.onClick

/**
 * Created by Hrant Arzumanyan on 05/30/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

class TermsOfUseFragment :
    BaseFragment<FragmentTermsOfUseBinding>({ FragmentTermsOfUseBinding.inflate(it) }) {
    override fun setupView() {
    }

    override fun setupToolbar() {
        binding.toolbarContainer.apply {
            titleTv.text = getString(R.string.terms)
            endImageView.hide()
            backButton.onClick {
                navigateUp()
            }
        }
    }
}