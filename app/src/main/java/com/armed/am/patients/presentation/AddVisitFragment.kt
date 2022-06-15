package com.armed.am.patients.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentAddVisitBinding
import com.armed.am.patients.network.model.AddVisitRequestModel
import com.armed.am.utils.*
import com.armed.am.utils.Constants.FUTURE_DATE_IS_SELECTED
import com.armed.am.utils.Constants.NO_RESULTS_FOUND
import com.armed.am.utils.Constants.REQUIRED_FIELD_IS_EMPTY
import com.armed.am.utils.Constants.START_DATE_IS_GREATER_THEN_END_DATE
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Levon Arzumanyan on 1/28/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

class AddVisitFragment :
    BaseFragment<FragmentAddVisitBinding>({ FragmentAddVisitBinding.inflate(it) }) {

    private val addVisitViewModel: AddVisitViewModel by viewModel()
    private val patientsViewModel: PatientsViewModel by viewModel()

    private val clinicsListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            patientsViewModel.getMiList()!!.map { it.name }
        )
    }

    private val visitModesListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            resources.getStringArray(R.array.visit_modes_list)
        )
    }

    private val visitTypesListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            resources.getStringArray(R.array.visit_types_list)
        )
    }

    private val reasonsOfTheVisitListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            resources.getStringArray(R.array.reasons_of_the_visit_list)
        )
    }

    private val sourceOfFinancingListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            resources.getStringArray(R.array.sources_of_financing_list)
        )
    }

    private val healthIssueTypesListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            resources.getStringArray(R.array.health_issue_types_list)
        )
    }

    private val healthIssueCaseTypesListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            resources.getStringArray(R.array.health_issue_case_types_list)
        )
    }

    override fun setupView() {
        setupExposedDropdownAdapters()
        setupDateAndTimeFields()
        handleSearchResults()
        binding.addVisitBtn.onClick {
            onAddVisitClick()
        }
    }

    private fun setupDateAndTimeFields() {
        binding.apply {
            setupTimeFieldBehaviour(visitStartTimeTv, visitStartTimeTvWrapper)
            setupTimeFieldBehaviour(visitEndTimeTv, visitEndTimeTvWrapper)
            setupTimeFieldBehaviour(healthIssueStartTimeTv, healthIssueStartTimeTvWrapper)
            setupDateFieldBehaviour(visitStartDateTv, visitStartDateTvWrapper)
            setupDateFieldBehaviour(visitEndDateTv, visitEndDateTvWrapper)
            setupDateFieldBehaviour(healthIssueStartDateTv, healthIssueStartDateTvWrapper)
        }
    }

    private fun setupExposedDropdownAdapters() {
        binding.apply {
            roleUuidExposedDropdown.setAdapter(clinicsListAdapter)
            visitModeExposedDropdown.setAdapter(visitModesListAdapter)
            visitTypeExposedDropdown.setAdapter(visitTypesListAdapter)
            reasonOfTheVisitExposedDropdown.setAdapter(reasonsOfTheVisitListAdapter)
            sourceOfFinancingExposedDropdown.setAdapter(sourceOfFinancingListAdapter)
            healthIssueTypeExposedDropdown.setAdapter(healthIssueTypesListAdapter)
            healthIssueCaseTypeExposedDropdown.setAdapter(healthIssueCaseTypesListAdapter)
        }
    }

    private fun getRoleUuid(selectionId: Int) =
        if (selectionId >= 0) {
            patientsViewModel.getMiList()!![selectionId].role_uuid
        } else ""

    private fun setupTimeFieldBehaviour(
        timeTextInputLayout: TextInputLayout,
        wrapperLayout: FrameLayout
    ) {
        timeTextInputLayout.apply {
            fun handleTimePick() {
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { view, hourOfDay, minute ->
                        this.editText?.setText(timeToSqlFormatWithoutSeconds(hourOfDay, minute))
                        isEnabled = false
                    }, 0, 0, true
                )
                timePickerDialog.show()
                runDelayed(5) { hideKeyboard() }
            }

            this.editText?.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    handleTimePick()
                    wrapperLayout.apply {
                        show()
                        onClick {
                            handleTimePick()
                        }
                    }
                }
            }
        }
    }

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
                            month+1,
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

    private fun onAddVisitClick() {
        binding.apply {
            addVisitViewModel.addVisit(
                AddVisitRequestModel(
                    role_uuid = getRoleUuid(roleUuidExposedDropdown.getSelectedItemPosition()),
                    patient_social_card = patientSocialCard.editText?.text.toString(),
                    patient_mobile_number = patientMobileNumber.editText?.text.toString(),
                    visit_start = connectDateAndTime(
                        visitStartDateEt.text.toString().formatDate("dd.MM.yyyy", "yyyy-MM-dd"),
                        visitStartTimeEt.text.toString()
                    ),
                    visit_end = connectDateAndTime(
                        visitEndDateEt.text.toString().formatDate("dd.MM.yyyy", "yyyy-MM-dd"),
                        visitEndTimeEt.text.toString()
                    ),
                    //in case of all dropdowns(except health_issue_type dropdown) we need
                    // to send int keys to the backend and to get correct keys we need to
                    // increment the selected item id by one as ordering in arrays are set by docs.
                    visit_mode = incrementExposedDropdownSelectedItemId(
                            visitModeExposedDropdown.getSelectedItemPosition()
                    ),
                    visit_type = incrementExposedDropdownSelectedItemId(
                        visitTypeExposedDropdown.getSelectedItemPosition()
                    ),
                    reason_of_the_visit = incrementExposedDropdownSelectedItemId(
                        reasonOfTheVisitExposedDropdown.getSelectedItemPosition()
                    ),
                    source_of_financing = incrementExposedDropdownSelectedItemId(
                        sourceOfFinancingExposedDropdown.getSelectedItemPosition()
                    ),
                    //in case of health_issue_type we need to send string keys to the backend and the ordering is same as in selection dropdown array
                    health_issue_type = if (healthIssueTypeExposedDropdown.getSelectedItemPosition() >= 0
                    ) resources.getStringArray(R.array.health_issue_types_keys_list)[
                            healthIssueTypeExposedDropdown.getSelectedItemPosition()] else "",
                    health_issue_case_type = incrementExposedDropdownSelectedItemId(
                        healthIssueCaseTypeExposedDropdown.getSelectedItemPosition()
                    ),
                    health_issue_start_date = connectDateAndTime(
                        healthIssueStartDateEt.text.toString()
                            .formatDate("dd.MM.yyyy", "yyyy-MM-dd"),
                        healthIssueStartTimeEt.text.toString()
                    ),
                    health_issue_case_number = healthIssueCaseNumber.editText?.text.toString(),
                    patient_email = patientEmail.editText?.text.toString(),
                    patient_first_name_EN = patientName.editText?.text.toString(),
                    patient_last_name_EN = patientSurname.editText?.text.toString()
                )
            )
        }
    }

    override fun setupToolbar() {
        binding.toolbarContainer.apply {
            backButton.onClick { navigateUp() }
            titleTv.text = getText(R.string.add_visit)
            endImageView.onClick {
                navigate(AddVisitFragmentDirections.actionAddVisitFragmentToSearchTypesFragment(true))
            }
        }
    }

    override fun setupObservers() {
        addVisitViewModel.getLoadingLiveData()
            .observe(this, { if (it) showLoading() else hideLoading() })
        addVisitViewModel.getErrorLiveData().observe(this, {
            showLongToast(it, requireContext())
        })
        addVisitViewModel.getErrorCodeLiveData().observe(this, {
            when (it) {
                REQUIRED_FIELD_IS_EMPTY -> showLongToast(
                    getString(R.string.please_fill_in_all_required_fields),
                    requireContext()
                )
                START_DATE_IS_GREATER_THEN_END_DATE -> showLongToast(
                    getString(R.string.visit_end_can_not_be_earlier_than_visit_start),
                    requireContext()
                )
                FUTURE_DATE_IS_SELECTED -> showLongToast(
                    getString(R.string.health_issue_start_can_not_be_with_future_date),
                    requireContext()
                )
                NO_RESULTS_FOUND -> showLongToast(
                    getString(R.string.no_results_found),
                    requireContext()
                )
            }
        })
        addVisitViewModel.getIsVisitAddedLiveData().observe(this, {
            if (it) showLongToast(
                getString(R.string.visit_added_successfully),
                requireContext()
            )
        })
        addVisitViewModel.getPersonSuggestionLiveData().observe(this, {
            binding.apply {
                patientNameEt.setText(it.name)
                patientSurnameEt.setText(it.surname)
                patientSocialCardEt.setText(it.socialCard)
            }
        })
    }

    @LayoutRes
    private fun getAdapterItemLayout(): Int {
        return R.layout.item_add_visit_dropdown
    }

    private fun connectDateAndTime(sqlFormatDate: String, sqlFormatTime: String) =
        "$sqlFormatDate $sqlFormatTime:00"

    //All values in String Arrays are ordered so that the correct request key for them will be id + 1
    private fun incrementExposedDropdownSelectedItemId(id: Int) = if (id >= 0) id + 1 else -1

    private fun handleSearchResults() {
        if (arguments?.getString(Constants.QUERY).isNullOrEmpty().not()) {
            addVisitViewModel.getPersonSuggestion(arguments?.getString(Constants.QUERY) ?: "")
        }
    }

}