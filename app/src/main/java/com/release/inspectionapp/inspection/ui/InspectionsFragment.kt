package com.release.inspectionapp.inspection.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.release.core_di.component.CoreInjectHelper
import com.release.inspectionapp.databinding.FragmentInspectionsBinding
import com.release.inspectionapp.inspection.di.component.DaggerInspectionComponent
import com.release.inspectionapp.inspection.ui.adaper.InspectionItemClickListener
import com.release.inspectionapp.inspection.ui.adaper.SavedAdapter
import com.release.inspectionapp.inspection.ui.mvi.InspectionIntent
import com.release.inspectionapp.inspection.ui.mvi.InspectionState
import com.release.inspectionapp.inspection.ui.mvi.Inspections
import com.release.inspectionapp.utils.livedata.observeEvent
import com.release.inspectionapp.utils.mvi.IView
import com.release.inspectionapp.utils.presentation.BaseFragment
import kotlinx.coroutines.launch

class InspectionsFragment : BaseFragment(), InspectionItemClickListener, IView<InspectionState> {

    private var _binding: FragmentInspectionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InspectionsViewModel by viewModels { viewModelFactory }

    private val adapter = SavedAdapter(this)

    override fun onAttach(context: Context) {
        DaggerInspectionComponent
            .builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(requireActivity().applicationContext))
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInspectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvSavedInspections.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.intents.send(InspectionIntent.ShowInspections)
        }

        binding.btnSignOUt.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intents.send(InspectionIntent.SignOut)
            }
        }

        binding.btnStart.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intents.send(InspectionIntent.FetchInspections)
            }
        }

        binding.btnSend.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intents.send(InspectionIntent.SendFinishedInspection)
            }
        }

        viewModel.navigationEvent.observeEvent(viewLifecycleOwner) {
            navigate(it)
        }
    }

    override fun onResumeInspectionClicked(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.intents.send(InspectionIntent.GoForward(id))
        }
    }

    override fun render(state: InspectionState) {
        when (state.inspections) {
            is Inspections.Loading -> {}
            is Inspections.SavedInspections -> adapter.updateList(state.inspections.inspectionItems)
            is Inspections.ReceivedInspections -> adapter.updateList(state.inspections.inspectionItems)
            is Inspections.NoSavedInspections -> {}
            is Inspections.UserSignedOut -> {}
            is Inspections.InspectionSent -> showDialog(state.inspections.message)
            is Inspections.ErrorMessage -> showDialog(state.inspections.msg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}