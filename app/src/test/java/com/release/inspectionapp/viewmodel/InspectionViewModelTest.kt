package com.release.inspectionapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.release.domain.model.InspectionItem
import com.release.domain.usecase.None
import com.release.domain.usecase.auth.SignOutUseCase
import com.release.domain.usecase.inspection.GetSavedInspectionUseCase
import com.release.domain.usecase.inspection.SendInspectionUseCase
import com.release.domain.usecase.inspection.StartInspectionUseCase
import com.release.inspectionapp.inspection.ui.InspectionsViewModel
import com.release.inspectionapp.inspection.ui.mvi.InspectionIntent
import com.release.inspectionapp.inspection.ui.mvi.InspectionState
import com.release.inspectionapp.inspection.ui.mvi.Inspections
import com.release.inspectionapp.utils.ResourceManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class InspectionViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    lateinit var viewModel: InspectionsViewModel

    @Mock
    lateinit var signOutUseCase: SignOutUseCase

    @Mock
    lateinit var getSavedInspectionsUseCase: GetSavedInspectionUseCase

    @Mock
    lateinit var sendInspectionUseCase: SendInspectionUseCase

    @Mock
    lateinit var requestStartInspectionUseCase: StartInspectionUseCase

    @Mock
    lateinit var resourceManager: ResourceManager

    @Mock
    private lateinit var observer: Observer<InspectionState>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = InspectionsViewModel(
            signOutUseCase,
            getSavedInspectionsUseCase,
            sendInspectionUseCase,
            requestStartInspectionUseCase,
            resourceManager
        )
        observer = Observer<InspectionState> {}
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `getSavedInspectionUseCase state successfully changed`() {
        val inspectionItems = getFakeListOfInspectionItems()
        val showSavedInspectionsIntent = InspectionIntent.ShowInspections
        val params = None
        val expectedViewState = InspectionState(Inspections.SavedInspections(inspectionItems))

        runBlocking {
            Mockito.`when`(getSavedInspectionsUseCase.execute(params)).thenReturn(inspectionItems)
            viewModel.intents.send(showSavedInspectionsIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertEquals(actualViewState.value!!.inspections, expectedViewState.inspections)
    }

    @Test
    fun `getNoStoredInspections state successfully changed`() {
        val inspectionItems = listOf<InspectionItem>()
        val showSavedInspectionsIntent = InspectionIntent.ShowInspections
        val params = None
        val expectedViewState = InspectionState(Inspections.NoSavedInspections)

        runBlocking {
            Mockito.`when`(getSavedInspectionsUseCase.execute(params)).thenReturn(inspectionItems)
            viewModel.intents.send(showSavedInspectionsIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertEquals(actualViewState.value!!.inspections, expectedViewState.inspections)
    }

    @Test
    fun `requestInspectionUseCase state successfully changed`() {
        val inspectionItems = listOf<InspectionItem>()
        val fetchInspectionsIntent = InspectionIntent.FetchInspections
        val params = None
        val expectedViewState = InspectionState(Inspections.NoSavedInspections)

        runBlocking {
            Mockito.`when`(requestStartInspectionUseCase.execute(params))
                .thenReturn(inspectionItems)
            viewModel.intents.send(fetchInspectionsIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertEquals(actualViewState.value!!.inspections, expectedViewState.inspections)
    }

    @Test
    fun `signoutUseCase state successfully changed`() {
        val params = None
        val response = Unit
        val signOutIntent = InspectionIntent.SignOut
        val expectedViewState = InspectionState(Inspections.UserSignedOut)

        runBlocking {
            Mockito.`when`(signOutUseCase.execute(params)).thenReturn(response)
            viewModel.intents.send(signOutIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertEquals(actualViewState.value!!.inspections, expectedViewState.inspections)
    }
}