package com.armed.am.patients.presentation

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentPatientsDetailsBinding
import com.armed.am.survey.presentation.SurveyViewModel
import com.armed.am.utils.*
import com.armed.am.utils.Constants.PATTERN_HHmm_COLON
import com.armed.am.utils.Constants.PATTERN_ddMMyyyy_DOT
import com.armed.am.utils.Constants.PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON
import com.armed.am.utils.Constants.PATTERN_yyyyMMdd_MINUSE
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class PatientDetailsFragment :
    BaseFragment<FragmentPatientsDetailsBinding>({ FragmentPatientsDetailsBinding.inflate(it) }) {

    private val args: PatientDetailsFragmentArgs by navArgs()
    private val surveyViewModel: SurveyViewModel by sharedViewModel()

    override fun setupView() {
        setUserInfo()
        binding.apply {
            startSurveyBtn.setOnClickListener {
                surveyViewModel.clearQuestionsArray()
                surveyViewModel.clearQuestionsLiveData()
                navigate(
                    PatientDetailsFragmentDirections
                        .actionPatientDetailsFragmentToSurveyFragment(
                            args.patientUiModel.genderCode,
                            args.patientUiModel.dateOfBirth.getYearsFromDate(PATTERN_yyyyMMdd_MINUSE)
                        )
                )
            }
        }
    }

    override fun setupToolbar() {
        binding.toolbarContainer.apply {
            backButton.onClick { findNavController().popBackStack() }
            titleTv.text = getString(R.string.patients)
            endImageView.hide()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUserInfo() {
        binding.apply {
            profileImage.displayRoundedImage(args.patientUiModel.imageUrl)
            nameSureName.text = "${args.patientUiModel.name} ${args.patientUiModel.sureName}"
            hideFieldIfTextIsEmpty(
                args.patientUiModel.dateOfBirth.formatDate(
                    PATTERN_yyyyMMdd_MINUSE,
                    PATTERN_ddMMyyyy_DOT
                ), birthdayTitleTv, birthdayDateBody
            )
            hideFieldIfTextIsEmpty(
                args.patientUiModel.mobilePhoneNumber,
                phoneNumberTitleTv,
                phoneNumberBody
            )
            hideFieldIfTextIsEmpty(args.patientUiModel.email, emailTitleTv, emailBody)
            hideFieldIfTextIsEmpty(args.patientUiModel.socialCard, socialCardTv, socialCardBody)
            hideFieldIfTextIsEmpty(args.patientUiModel.passport, passportTitleTv, passportBody)
            hideFieldIfTextIsEmpty(args.patientUiModel.address, addressTitleTv, addressBody)
            hideFieldIfTextIsEmpty(args.patientUiModel.socialGroup, socialGroupTv, socialGroupBody)
            hideFieldIfTextIsEmpty(args.patientUiModel.caseType, caseTypeTv, caseTypeBody)
            hideFieldIfTextIsEmpty(args.patientUiModel.diagnose, diagnoseTv, diagnoseBody)

            visitDate.text =
                args.patientUiModel.visitStart.formatDate(
                    PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON,
                    PATTERN_ddMMyyyy_DOT
                )
            visitTime.text =
                "${
                    args.patientUiModel.visitStart.formatDate(
                        PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON,
                        PATTERN_HHmm_COLON
                    )
                } -" +
                        " ${
                            args.patientUiModel.visitEnd.formatDate(
                                PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON,
                                PATTERN_HHmm_COLON
                            )
                        }"
        }
    }

    private fun hideFieldIfTextIsEmpty(text: String, titleView: TextView, bodyView: TextView) {
        if (text.isEmpty()) {
            titleView.hide()
            bodyView.hide()
        } else {
            titleView.show()
            bodyView.apply {
                show()
                setText(text)
            }
        }
    }

}


