package com.release.inspectionapp.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.release.core_di.component.CoreInjectHelper
import com.release.inspectionapp.R
import com.release.inspectionapp.app.di.component.DaggerAppComponent
import com.release.inspectionapp.app.ui.mvi.App
import com.release.inspectionapp.app.ui.mvi.AppIntent
import com.release.inspectionapp.app.ui.mvi.AppState
import com.release.inspectionapp.databinding.ActivityAppBinding
import com.release.inspectionapp.utils.mvi.IView
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppActivity : AppCompatActivity(), IView<AppState> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AppActivityViewModel by viewModels { viewModelFactory }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent
            .builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext))
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.app_fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        viewModel.state.observe(this) { render(it) }
        lifecycleScope.launch { viewModel.intents.send(AppIntent.UserEnteredTheApp) }
        setContentView(binding.root)
    }

    override fun render(state: AppState) {
        when (state.app) {
            is App.Loading -> {}
            is App.UserEnteredTheApp -> {
                val navGraph = navHostFragment.navController.navInflater.inflate(state.app.graph)
                navGraph.startDestination = state.app.destination
                navController.graph = navGraph
            }
            is App.ErrorMessage -> Toast.makeText(this, state.app.msg, Toast.LENGTH_LONG).show()
        }
    }
}