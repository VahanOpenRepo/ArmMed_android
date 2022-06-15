package com.armed.am.patients.search

import android.app.DatePickerDialog
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentSearchBinding
import com.armed.am.utils.*
import com.armed.am.utils.Constants.QUERY
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject

/**
 * Created by Levon Arzumanyan on 12/1/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class SearchFragment : BaseFragment<FragmentSearchBinding>({ FragmentSearchBinding.inflate(it) }) {
    private val args: SearchFragmentArgs by navArgs()

    override fun setupView() {
        handleSearchType()
        binding.apply {
            setupDateFieldBehaviour(birthDateTextField, birthDateTextFieldWrapper)
            searchBtn.onClick {
                context?.let {
                    if (areInputsEmpty(
                            requireContext(),
                            nameTextField,
                            surnameTextField,
                            birthDateTextField,
                            socialCardDateTextField
                        )
                    ) {
                        if (args.isFromAddVisit) {
                            navigate(
                                R.id.action_searchFragment_to_addVisitFragment,
                                bundleOf(QUERY to getSearchQueryString())
                            )
                        } else {
                            navigate(
                                R.id.action_searchFragment_to_patientsFragment,
                                bundleOf(QUERY to getSearchQueryJson())
                            )
                        }

                    }

                }
            }

        }
    }

    override fun setupToolbar() {
        backButton.onClick { navigateUp() }
        endImageView.invisible()
        titleTv.invisible()
    }

    private fun handleSearchType() {
        when (args.searchType) {
            SearchTypesEnum.BY_NAME_SURNAME -> showNameSurnameSearch()
            SearchTypesEnum.BY_NAME_SURNAME_AND_BIRTHDAY_DATE -> showNameSurnameBirthDateSearch()
            SearchTypesEnum.BY_SOCIAL_CARD_OR_PASSPORT -> showSocialCardSearch()
        }
    }

    private fun showNameSurnameSearch() {
        binding.apply {
            nameTextField.show()
            surnameTextField.show()
            birthDateTextField.hide()
            socialCardDateTextField.hide()
        }
    }

    private fun showNameSurnameBirthDateSearch() {
        binding.apply {
            nameTextField.show()
            surnameTextField.show()
            birthDateTextField.show()
            socialCardDateTextField.hide()
        }
    }

    private fun showSocialCardSearch() {
        binding.apply {
            nameTextField.hide()
            surnameTextField.hide()
            birthDateTextField.hide()
            socialCardDateTextField.show()
        }
    }

    private fun getSearchQueryJson(): String {
        var queryJson = ""
        binding.apply {
            queryJson = JSONObject().put("patient_name", nameTextField.editText?.text)
                .put("patient_last_name", surnameTextField.editText?.text)
                .put("patient_ssn", socialCardDateTextField.editText?.text)
                .put("date_of_birth", birthDateTextField.editText?.text).toString()
        }
        return queryJson
    }

    private fun getSearchQueryString(): String {
        var queryString = ""
        binding.apply {
            queryString =
                "${socialCardDateTextField.editText?.text ?: ""} ${nameTextField.editText?.text ?: ""} ${surnameTextField.editText?.text ?: ""} ${birthDateTextField.editText?.text ?: ""}"
        }
        return queryString.trim()
    }

    //TODO code repeat same function exists in AddVisitFragment remove this in future
    private fun setupDateFieldBehaviour(
        dateTextInputLayout: TextInputLayout,
        wrapperLayout: FrameLayout
    ) {
        dateTextInputLayout.apply {

            fun handleDatePick() {
                val datePickerDialog = DatePickerDialog(requireContext())
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->

                    this.editText?.setText(
                        dateToSqlFormat(
                            year,
                            month + 1,
                            dayOfMonth
                        ).formatDate("yyyy-MM-dd", "dd.MM.yyyy")
                    )
                    isEnabled = false
                }
                datePickerDialog.show()
                runDelayed(5) { hideKeyboard() }
            }

            this.editText?.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    handleDatePick()
                    wrapperLayout.apply {
                        show()
                        isClickable = true
                        onClick {
                            handleDatePick()
                        }
                    }
                }
            }
        }
    }
}