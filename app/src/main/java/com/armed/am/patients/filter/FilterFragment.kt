package com.armed.am.patients.filter

import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentFilterBinding
import com.armed.am.patients.presentation.PatientsViewModel
import com.armed.am.utils.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Levon Arzumanyan on 2/7/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

class FilterFragment : BaseFragment<FragmentFilterBinding>({ FragmentFilterBinding.inflate(it) }) {

    private val patientsViewModel: PatientsViewModel by viewModel()
    private val clinicsListAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            getAdapterItemLayout(),
            patientsViewModel.getMiList()!!.map { it.name }
        )
    }

    override fun setupView() {
        binding.apply {
            roleUuidExposedDropdown.setAdapter(clinicsListAdapter)
            filterBtn.onClick {
                if (patientsViewModel.validateFilterClinicsDropdownInput(roleUuidExposedDropdown.getSelectedItemPosition())) {
                    navigate(
                        R.id.action_filterFragment_to_patientsFragment,
                        bundleOf(Constants.FILTER to getMiId(roleUuidExposedDropdown.getSelectedItemPosition()))
                    )
                } else showLongToast(
                    getString(R.string.please_fill_in_all_fields),
                    requireContext()
                )
            }
        }
    }

    override fun setupToolbar() {
        backButton.onClick { navigateUp() }
        endImageView.invisible()
        titleTv.text = getString(R.string.filter_by)
    }

    private fun getMiId(selectionId: Int) = patientsViewModel.getMiList()!![selectionId].id

    @LayoutRes
    private fun getAdapterItemLayout(): Int {
        return R.layout.item_add_visit_dropdown
    }

}
