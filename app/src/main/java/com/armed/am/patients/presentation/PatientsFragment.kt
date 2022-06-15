package com.armed.am.patients.presentation

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.armed.am.R
import com.armed.am.base.BaseFragment
import com.armed.am.databinding.FragmentPatientsBinding
import com.armed.am.patients.network.model.PatientUIModel
import com.armed.am.patients.presentation.adapter.PatientsAdapter
import com.armed.am.utils.*
import com.armed.am.utils.Constants.FILTER
import com.armed.am.utils.Constants.QUERY
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class PatientsFragment :
    BaseFragment<FragmentPatientsBinding>({ FragmentPatientsBinding.inflate(it) }) {

    private val adapter by lazy { PatientsAdapter(::onPatientItemClicked) }
    private val patientsViewModel: PatientsViewModel by viewModel()

    companion object {
        private const val MI_ID = "mi_id"
    }

    override fun setupView() {
        if (patientsViewModel.isUserLoggedIn().not()) navigate(R.id.authorization) else {
            initRecyclerView()
            changeStatusBarColor(R.color.white)
            handleSearchAndFilterResults()
            handleFabBehaviour()
        }
    }

    private fun setupDefaultToolbar() {
        binding.toolbarContainer.apply {
            backButton.invisible()
            titleTv.text = getString(R.string.patients)
            endImageView.onClick {
                navigate(PatientsFragmentDirections.actionPatientsFragmentToSearchTypesFragment(false))
            }
            showFiltersIfMiIsNotEmpty()
        }
    }

    private fun showFiltersIfMiIsNotEmpty() {
        binding.toolbarContainer.firstImageView.apply {
            if (this.isVisible.not()) {
                isVisible = isDoctorWorksInMultipleClinics()
                onClick {
                    navigate(R.id.action_patientsFragment_to_filterFragment)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            patientsRv.adapter = adapter
            loadData()
            adapter.addLoadStateListener { state ->
                patientsRv.isVisible = state.refresh != LoadState.Loading

                when (val currentState = state.refresh) {
                    is LoadState.Loading -> showLoading()
                    is LoadState.NotLoading -> {
                        showEmptyScreen(adapter.itemCount < 1)
                        hideLoading()
                        showFiltersIfMiIsNotEmpty()
                    }
                    is LoadState.Error -> {
                        showLongToast(
                            currentState.error.message ?: getString(R.string.generic_error_message),
                            requireContext()
                        )
                        hideLoading()
                    }
                    else -> hideLoading()
                }
            }
        }
    }

    private fun handleFabBehaviour() {
        binding.apply {
            patientsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // if the recycler view is scrolled
                    // above shrink the FAB
                    if (dy > 10 && fab.isExtended) {
                        fab.shrink()
                    }

                    // if the recycler view is scrolled
                    // above extend the FAB
                    if (dy < -10 && !fab.isExtended) {
                        fab.extend()
                    }

                    // of the recycler view is at the first
                    // item always extend the FAB
                    if (!recyclerView.canScrollVertically(-1)) {
                        fab.extend()
                    }
                }
            })
            fab.onClick {
                navigate(R.id.action_patientsFragment_to_addVisitFragment)
            }
        }
    }

    private fun onPatientItemClicked(data: PatientUIModel?) =
        navigate(PatientsFragmentDirections.actionPatientsFragmentToPatientDetailsFragment(data!!))

    private fun handleSearchAndFilterResults() {
        if (arguments?.getString(QUERY).isNullOrEmpty().not() || arguments?.getString(FILTER)
                .isNullOrEmpty().not()
        ) {
            binding.toolbarContainer.apply {
                backButton.apply {
                    setImageResource(R.drawable.ic_close)
                    onClick {
                        getPatients()
                        setupDefaultToolbar()
                    }
                }
                titleTv.text = getString(R.string.results)
                endImageView.onClick {
                    navigate(R.id.action_patientsFragment_to_searchTypesFragment)
                }
            }
            arguments?.clear()
        } else {
            setupDefaultToolbar()
        }
    }

    override fun loadData() {
        getPatients()
    }

    private fun getPatients() {
        lifecycleScope.launch {
            patientsViewModel.getPatients(
                JSONObject(arguments?.getString(QUERY) ?: "{}").put(
                    MI_ID,
                    arguments?.getString(FILTER) ?: ""
                )
            )
                .collect() { pagingData ->
                    adapter.submitData(pagingData)
                }
        }
    }

    private fun showEmptyScreen(show: Boolean) {
        if (show) {
            binding.layoutNoResults.apply {
                root.show()
                refreshBtn.onClick {
                    getPatients()
                    setupDefaultToolbar()
                }
            }
        } else binding.layoutNoResults.root.hide()
    }

    private fun isDoctorWorksInMultipleClinics(): Boolean =
        patientsViewModel.getMiList()?.let { it.size > 1 } ?: false

}


