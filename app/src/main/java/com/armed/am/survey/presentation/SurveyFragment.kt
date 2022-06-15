package com.armed.am.survey.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentSurveyBinding
import com.armed.am.survey.network.model.AnswerParamsModel
import com.armed.am.survey.network.model.QuestionParamsModel
import com.armed.am.utils.*
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * Created by Levon Arzumanyan on 10/07/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class SurveyFragment :
    BaseFragment<FragmentSurveyBinding>({ FragmentSurveyBinding.inflate(it) }) {

    private val surveyViewModel: SurveyViewModel by sharedViewModel()
    private val args: SurveyFragmentArgs by navArgs()

    override fun setupView() {
        getFirstQuestion()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    handleBackPressed()
                }
            })
    }

    override fun setupObservers() {
        surveyViewModel.getLoadingLiveData().observe(this, {
            if (it) showLoading() else hideLoading()
        })
        surveyViewModel.getErrorLiveData().observe(this, {
            showLongToast(it, requireContext())
        })
        surveyViewModel.getQuestionsLiveData().observe(this, { response ->
            response?.let { drawCorrectViewByQuestionType(it) }
        })
    }

    override fun setupToolbar() {
        binding.apply {
            toolbarContainer.backButton.onClick { handleBackPressed() }
            endImageView.hide()
        }
    }

    private fun drawCorrectViewByQuestionType(response: QuestionParamsModel) {
        when (response.variable_type_code) {
            "1" -> handleBooleanQuestion(response)
            "2" -> handleNumberQuestion(response)
            "3", "9" -> handleDictionaryQuestion(response)
            "5" -> handleTextQuestion(response)
            "6", "7", "8" -> handleDateTimeQuestions(response)
            else -> if (response.fileUrl.isNotEmpty()) requireActivity().openWebPage(response.fileUrl)
            else showLongToast("Unsupported question type", requireContext())
        }
    }

    private fun handleDateTimeQuestions(response: QuestionParamsModel) {

        var sqlFormatDate = ""
        var sqlFormatTime = ""
        var sqlFormatDateOrAndTime = ""

        //inflating question layout
        val questionLayout = layoutInflater.inflate(R.layout.date_time_select_layout, null)
        //setting the data to question layout
        questionLayout.rootView.findViewById<AppCompatTextView>(R.id.question_tv).text =
            response.question
        val dateTextView =
            questionLayout.rootView.findViewById<TextInputLayout>(R.id.date_tv)
        val timeTextView =
            questionLayout.rootView.findViewById<TextInputLayout>(R.id.time_tv)

        val dateWrapper =
            questionLayout.rootView.findViewById<FrameLayout>(R.id.date_tv_wrapper)
        val timeWrapper =
            questionLayout.rootView.findViewById<FrameLayout>(R.id.time_tv_wrapper)

        dateTextView?.apply {

            fun handleDatePick() {
                val datePickerDialog = DatePickerDialog(requireContext())
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    sqlFormatDate = dateToSqlFormat(year, month+1, dayOfMonth)
                    this.editText?.setText(sqlFormatDate.formatDate("yyyy-MM-dd", "dd.MM.yyyy"))
                    isEnabled = false
                }
                datePickerDialog.show()
                runDelayed(5) { hideKeyboard() }
            }

            this.editText?.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    handleDatePick()
                    dateWrapper.apply {
                        show()
                        isClickable = true
                        onClick {
                            handleDatePick()
                        }
                    }
                }
            }
        }

        timeTextView?.apply {

            fun handleTimePick() {
                val timePickerDialog = TimePickerDialog(

                    requireContext(),
                    { view, hourOfDay, minute ->
                        sqlFormatTime = timeToSqlFormat(hourOfDay, minute)
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
                    timeWrapper.apply {
                        show()
                        onClick {
                            handleTimePick()
                        }
                    }
                }
            }
        }

        when (response.variable_type_code) {
            "6" -> {
                dateTextView.show()
                timeTextView.hide()
            }
            "7" -> {
                dateTextView.show()
                timeTextView.show()
            }
            "8" -> {
                dateTextView.hide()
                timeTextView.show()
            }
        }

        binding.apply {
            //adding the inflated view to container
            questionLayoutContainer.apply {
                removeAllViews()
                addView(questionLayout)
                (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.NO_GRAVITY
            }

            forwardBtn.onClick {
                when (response.variable_type_code) {
                    "6" -> {
                        sqlFormatDateOrAndTime = sqlFormatDate
                    }
                    "7" -> {
                        if (sqlFormatDate.isNotEmpty() && sqlFormatTime.isNotEmpty()) {
                            sqlFormatDateOrAndTime = "$sqlFormatDate $sqlFormatTime"
                        }
                    }
                    "8" -> {
                        sqlFormatDateOrAndTime = sqlFormatTime
                    }
                }
                if (sqlFormatDateOrAndTime.showPopupIfEmpty(requireContext()).not()) {
                    surveyViewModel.getQuestions(
                        answerParams = arrayOf(
                            AnswerParamsModel(
                                response.question,
                                response.name,
                                sqlFormatDateOrAndTime
                            )
                        )
                    )
                }
            }
        }
    }

    private fun handleDictionaryQuestion(response: QuestionParamsModel) {
        var checkedRadioBtnCode = ""
        //inflating question layout
        val questionLayout =
            layoutInflater.inflate(R.layout.dictionary_radio_group_layout, null)

        //setting the data to question layout and adding radioButtons to radioGroup
        questionLayout.rootView.findViewById<AppCompatTextView>(R.id.question_tv).text =
            response.question
        val radioGroup = questionLayout.findViewById<RadioGroup>(R.id.options_radio_group)
        val radioButtons =
            Array(response.dictionary_content.size) { i -> RadioButton(requireContext()) }

        for (i in response.dictionary_content.indices) {
            radioButtons[0].isSelected = true
            val font =
                ResourcesCompat.getFont(requireContext(), R.font.noto_sans_armenian_regular)

            radioButtons[i].apply {
                id = i + 100
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black_light))
                setButtonDrawable(R.drawable.selector_custom_radio_btn)
                text = response.dictionary_content[i].text_AM
                textSize = 13f
                typeface = font
                radioGroup.addView(this)
                setPadding(dpToPx(requireContext(), 8f))
                (layoutParams as? RadioGroup.LayoutParams)?.setMargins(
                    0,
                    dpToPx(requireContext(), 8f),
                    0,
                    0
                )
            }
        }

        //setting listener to the radio group and getting the value to send to server
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            checkedRadioBtnCode = response.dictionary_content[checkedId - 100].code
        }
        binding.apply {
            //adding the inflated view to container
            questionLayoutContainer.apply {
                removeAllViews()
                addView(questionLayout)
                (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.NO_GRAVITY
            }

            forwardBtn.onClick {
                if (checkedRadioBtnCode.showPopupIfEmpty(requireContext()).not()) {
                    surveyViewModel.getQuestions(
                        answerParams = arrayOf(
                            AnswerParamsModel(
                                response.question,
                                response.name,
                                checkedRadioBtnCode
                            )
                        )
                    )
                }
            }
        }
    }

    private fun handleNumberQuestion(response: QuestionParamsModel) {
        //inflating question layout
        val questionLayout = layoutInflater.inflate(R.layout.number_input_layout, null)
        //setting the data to question layout
        questionLayout.rootView.findViewById<AppCompatTextView>(R.id.question_tv).text =
            response.question
        questionLayout.rootView.findViewById<AppCompatTextView>(R.id.min_max_tv).text =
            getString(
                R.string.min_max,
                response.min_value.safeToDouble(),
                response.max_value.safeToDouble()
            )

        binding.apply {
            //adding the inflated view to container
            questionLayoutContainer.apply {
                removeAllViews()
                addView(questionLayout)
                (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.NO_GRAVITY
            }

            forwardBtn.onClick {
                val inputText =
                    questionLayout.rootView.findViewById<TextInputLayout>(R.id.input_et).editText?.text.toString()
                if (areInputsEmpty(
                        requireContext(),
                        questionLayout.rootView.findViewById(R.id.input_et)
                    ) &&
                    isInputValueValid(
                        response.min_value.safeToDouble(),
                        response.max_value.safeToDouble(),
                        inputText.safeToDouble(),
                        requireContext()
                    )
                ) {
                    surveyViewModel.getQuestions(
                        answerParams = arrayOf(
                            AnswerParamsModel(
                                response.question,
                                response.name,
                                questionLayout.rootView.findViewById<TextInputLayout>(R.id.input_et).editText?.text.toString()
                            )
                        )
                    )
                }
            }
            forwardBtn.show()
        }
    }

    private fun handleTextQuestion(response: QuestionParamsModel) {
        //inflating question layout
        val questionLayout = layoutInflater.inflate(R.layout.text_input_layout, null)
        //setting the data to question layout
        questionLayout.rootView.findViewById<AppCompatTextView>(R.id.question_tv).text =
            response.question

        binding.apply {
            //adding the inflated view to container
            questionLayoutContainer.apply {
                removeAllViews()
                addView(questionLayout)
                (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.NO_GRAVITY
            }

            forwardBtn.onClick {
                if (questionLayout.rootView.findViewById<TextInputLayout>(R.id.input_et).editText?.text.toString()
                        .showPopupIfEmpty(requireContext()).not()
                ) {
                    surveyViewModel.getQuestions(
                        answerParams = arrayOf(
                            AnswerParamsModel(
                                response.question,
                                response.name,
                                questionLayout.rootView.findViewById<TextInputLayout>(R.id.input_et).editText?.text.toString()
                            )
                        )
                    )
                }
            }
            forwardBtn.show()
        }
    }

    private fun handleBooleanQuestion(response: QuestionParamsModel) {
        //inflating question layout
        val questionLayout = layoutInflater.inflate(R.layout.boolean_input_layout, null)
        //setting the data to question layout
        questionLayout.rootView.findViewById<AppCompatTextView>(R.id.question_tv).text =
            response.question
        binding.apply {
            //adding the inflated view to container
            questionLayoutContainer.apply {
                removeAllViews()
                addView(questionLayout)
                (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
            }

            questionLayout.rootView.findViewById<AppCompatTextView>(R.id.question_tv).text =
                response.question
            questionLayout.rootView.findViewById<AppCompatTextView>(R.id.no_btn).onClick {
                surveyViewModel.getQuestions(
                    answerParams = arrayOf(
                        AnswerParamsModel(
                            response.question,
                            response.name,
                            "0"
                        )
                    )
                )
            }

            questionLayout.rootView.findViewById<AppCompatTextView>(R.id.yes_btn).onClick {
                surveyViewModel.getQuestions(
                    answerParams = arrayOf(
                        AnswerParamsModel(
                            response.question,
                            response.name,
                            "1"
                        )
                    )
                )
            }
            forwardBtn.hide()
        }
    }

    private fun handleBackPressed() {
        surveyViewModel.apply {
            surveyViewModel.removeItemsFromEndOfQuestionsArray(lastRequestedQuestionsCount)
            if (surveyViewModel.getLengthOfQuestionArray() > 2) getQuestions() else findNavController().popBackStack()
        }
    }

    private fun getFirstQuestion() {
        surveyViewModel.getQuestions(
            answerParams = arrayOf(
                AnswerParamsModel(Constants.AGE, Constants.AGE, args.age),
                AnswerParamsModel(Constants.GENDER, Constants.GENDER, args.genderCode)
            )
        )
    }

}


