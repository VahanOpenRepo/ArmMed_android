package com.armed.am.authorization.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentLoginWithQrBinding
import com.armed.am.setup.permissions.AppPermissions
import com.armed.am.utils.isPermissionGranted
import com.armed.am.utils.onClick
import com.armed.am.utils.showInfoDialog

/**
 * Created by Hrant Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class LoginWithQRFragment :
    BaseFragment<FragmentLoginWithQrBinding>({ FragmentLoginWithQrBinding.inflate(it) }) {

    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun setupView() {
        binding.apply {
            scanTv.onClick {
                onScanClick()
            }
            termsOfUseTv.onClick {
                findNavController().navigate(R.id.action_loginWithQR_to_termsOfUseFragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted ->
                if (isGranted) {
                    findNavController().navigate(R.id.action_loginWithQR_to_scanQRFragment)
                } else {
                    Log.i("Permission: ", "Denied")
                }
            }


        super.onCreate(savedInstanceState)
    }

    private fun onScanClick() {
        when {
            isPermissionGranted(AppPermissions.PERMISSION_CAMERA) -> {
                findNavController().navigate(R.id.action_loginWithQR_to_scanQRFragment)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                AppPermissions.PERMISSION_CAMERA
            ) -> {
                showInfoDialog(
                    R.string.ask_camera_permission_text,
                    R.string.camera_permission_title,
                    positiveButtonClickListener = {
                        requestPermissionLauncher.launch(AppPermissions.PERMISSION_CAMERA)
                    },
                    negativeButtonClickListener = {})
            }
            else -> requestPermissionLauncher.launch(AppPermissions.PERMISSION_CAMERA)
        }
    }
}
