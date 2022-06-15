package com.armed.am.main.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.armed.am.R
import com.armed.am.base.BaseActivity
import com.armed.am.databinding.ActivityMainBinding
import com.armed.am.utils.*
import com.armed.am.setup.network.utils.MyState
import com.armed.am.setup.network.utils.NetworkStatusViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Levon Arzumanyan on 09/20/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class MainActivity() : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {

    private val networkStatusViewModel: NetworkStatusViewModel by viewModel()
    private lateinit var bottomNavigationView: BottomNavigationView
    private var selectedTab: BottomNavigationTab? = null
    private val preferences: Preferences by inject()
    private var hideBottomNav = false
    private var currentNavController: LiveData<NavController>? = null
    private val destinationChangeListener =
        NavController.OnDestinationChangedListener { _, _, arguments ->
//            hideLoading()
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            if (arguments?.getBoolean("hideBottomNavBar") == true) {
                hideBottomNav=true
                hideBottomNav(arguments.getBoolean("animateBottomNavBar"))
                return@OnDestinationChangedListener
            }
            hideBottomNav=false
            showBottomNav()
        }

    override fun setupView(savedInstanceState: Bundle?, binding: ActivityMainBinding) {
        binding.apply {
            bottomNavigationView = bottomNav
        }
        if (savedInstanceState == null) setupBottomNavigationBar() // Else, need to wait for onRestoreInstanceState
        setupObservers()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        with(bottomNavigationView) {
            menu.apply {
                findItem(R.id.patients).apply {
                    title = getString(R.string.patients)
                }
                findItem(R.id.profile).apply {
                    title = getString(R.string.profile)
                    icon = ContextCompat.getDrawable(context, R.drawable.ic_profile)
                }
            }

            setupBackground(resources.getDimension(R.dimen.bottom_nav_radius))

            val navGraphIds = listOf(
                R.navigation.nav_patients,
                R.navigation.nav_notifications,
                R.navigation.nav_profile,
                R.navigation.nav_more
            )

            // Setup the bottom navigation view with a list of navigation graphs
            val controller = setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.navHostFragment,
                intent = intent
            ) { selectedMenuItem ->
                when (selectedMenuItem.itemId) {
                    R.id.patients -> {
                        selectedTab = BottomNavigationTab.TAB_PATIENTS
                    }
                    R.id.profile -> {
                        selectedTab = BottomNavigationTab.TAB_PROFILE
                    }
                }
            }

            // Whenever the selected controller changes, setup the action bar.
            controller.observe(this@MainActivity) { navController ->
                navController?.removeOnDestinationChangedListener(destinationChangeListener)
                navController?.addOnDestinationChangedListener(destinationChangeListener)
            }

            currentNavController = controller
            selectedTab = BottomNavigationTab.TAB_PATIENTS
        }
    }

    enum class BottomNavigationTab(val index: Int) {
        TAB_PATIENTS(0),
        TAB_PROFILE(1),
    }

    fun hideBottomNav(canAnimate: Boolean = false) {
        bottomNavigationView.apply {
            if (canAnimate) {
                animate()
                    .translationY(height.toFloat())
                    .alpha(0f)
                    .setDuration(100)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            visibility = View.GONE
                        }
                    }).start()
            } else {
                visibility = View.GONE
            }
        }
    }

    fun showBottomNav() {
        bottomNavigationView.apply {
            translationY = 1f
            alpha = 1f
            visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    fun selectTab(tab: BottomNavigationTab) {
        bottomNavigationView.selectTab(tab)
    }

    fun logout() {
        restartApp()
    }

    private fun restartApp() {
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
        preferences.clearValues()
    }

    private fun setupObservers() {
        networkStatusViewModel.state.observe(this@MainActivity) { state ->
            binding.apply {
                when (state) {
                    MyState.Fetched -> {
                        if (hideBottomNav.not()){
                            bottomNav.show()
                        }
                        noConnectionLayout.root.hide()
                    }
                    MyState.Error -> {
                        bottomNav.hide()
                        noConnectionLayout.root.show()
                    }
                }
            }
        }
    }

    fun showLoading() {
        binding.loadingLayout.root.show()
    }

    fun hideLoading() {
        binding.loadingLayout.root.hide()
    }

}
