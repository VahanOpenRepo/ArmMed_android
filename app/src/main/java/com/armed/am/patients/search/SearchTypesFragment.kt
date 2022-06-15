package com.armed.am.patients.search

import androidx.navigation.fragment.navArgs
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentSearchTypesBinding
import com.armed.am.utils.*

/**
 * Created by Levon Arzumanyan on 12/1/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class SearchTypesFragment :
    BaseFragment<FragmentSearchTypesBinding>({ FragmentSearchTypesBinding.inflate(it) }) {

    private val args: SearchTypesFragmentArgs by navArgs()

    override fun setupView() {
        enableSupportedSearchTypes()
        binding.apply {
            byNameSurnameTv.onClick {
                navigate(
                    SearchTypesFragmentDirections.actionSearchTypesFragmentToSearchFragment(
                        SearchTypesEnum.BY_NAME_SURNAME,
                        args.isFromAddVisit
                    )
                )
            }
            byNameSurnameBirthdateTv.onClick {
                navigate(
                    SearchTypesFragmentDirections.actionSearchTypesFragmentToSearchFragment(
                        SearchTypesEnum.BY_NAME_SURNAME_AND_BIRTHDAY_DATE,
                        args.isFromAddVisit
                    )
                )
            }
            bySocialCardOrPassportTv.onClick {
                navigate(
                    SearchTypesFragmentDirections.actionSearchTypesFragmentToSearchFragment(
                        SearchTypesEnum.BY_SOCIAL_CARD_OR_PASSPORT,
                        args.isFromAddVisit
                    )
                )
            }
        }
    }

    override fun setupToolbar() {
        binding.toolbar.apply {
            backButton.apply {
                setImageResource(R.drawable.ic_close)
                onClick { navigateUp() }
            }
            titleTv.text = getString(R.string.search_by)
            endImageView.hide()
        }
    }

    private fun enableSupportedSearchTypes() {
        binding.apply {
            if (args.isFromAddVisit) {
                bySocialCardOrPassportTv.show()
                byNameSurnameTv.hide()
                topDivider.hide()
                bottomDivider.show()
            } else {
                bySocialCardOrPassportTv.hide()
                byNameSurnameTv.show()
                topDivider.show()
                bottomDivider.hide()
            }
        }
    }

}