package com.armed.am.profile

import android.widget.TextView
import com.armed.am.R
import com.armed.am.authorization.network.model.LoginResponseModel
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentProfileBinding
import com.armed.am.utils.*
import com.armed.am.utils.GsonUtils.getProfileModelFromJson
import org.koin.android.ext.android.inject

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class ProfileFragment :
    BaseFragment<FragmentProfileBinding>({ FragmentProfileBinding.inflate(it) }) {
    private val preferences: Preferences by inject()

    override fun setupView() {
        fillData()
    }

    private fun fillData() {
        val profileUIModel: LoginResponseModel.Result? =
            preferences.getStringFromPreference(
                Preferences.PROFILE_UI_MODEL
            ).getProfileModelFromJson()

        binding.apply {
            profileImage.displayRoundedImage(profileUIModel?.profilePicture, R.drawable.ic_profile_placeholder )
            nameSureName.text = "${profileUIModel?.firstName} ${profileUIModel?.lastName} "
            hideFieldIfTextIsEmpty(profileUIModel?.email.orEmpty(),emailTitleTv, emailBody)
            hideFieldIfTextIsEmpty(profileUIModel?.dateOfBirth?.formatDate("yyyy-MM-dd", "dd.MM.yyyy").orEmpty(),birthdayTitleTv, birthdayDateBody)
            hideFieldIfTextIsEmpty(profileUIModel?.mobileNumber.orEmpty(),phoneNumberTitleTv, phoneNumberBody)
            hideFieldIfTextIsEmpty(profileUIModel?.socialCard.orEmpty(),socialCardTv, socialCardBody)
        }
    }

    override fun setupToolbar() {
        binding.toolbarContainer.apply {
            backButton.invisible()
            titleTv.text=getString(R.string.profile)
            endImageView.apply {
                setImageResource(R.drawable.ic_logout)
                onClick {
                    logout()
                }
            }
        }
    }

    private fun hideFieldIfTextIsEmpty(text:String, titleView: TextView, bodyView: TextView){
        if (text.isEmpty()) {
            titleView.hide()
            bodyView.hide()
        } else{
            titleView.show()
            bodyView.apply {
                show()
                setText(text)
            }
        }
    }
}