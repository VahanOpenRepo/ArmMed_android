package com.armed.am.authorization.presentation

import androidx.navigation.fragment.findNavController
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentScanQrBinding
import com.armed.am.utils.showLoading
import com.armed.am.utils.showLongToast
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Hrant Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class ScanQRFragment : BaseFragment<FragmentScanQrBinding>({ FragmentScanQrBinding.inflate(it) }) {

    private lateinit var codeScanner: CodeScanner
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun setupView() {
        binding.apply {
            codeScanner = CodeScanner(requireActivity(), scannerView)
            codeScanner.decodeCallback = DecodeCallback {
                loginViewModel.login(it.text)
            }
            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    override fun setupObservers() {
        loginViewModel.getLoadingLiveData().observe(this) {
            showLoading()
        }
        loginViewModel.getErrorLiveData().observe(this) {
            showLongToast(it, requireContext())
        }
        loginViewModel.getLoginResultLiveData().observe(this) {
            findNavController().popBackStack(R.id.authorization, true)
        }
    }
}
