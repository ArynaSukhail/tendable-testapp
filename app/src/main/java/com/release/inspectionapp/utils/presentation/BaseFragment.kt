package com.release.inspectionapp.utils.presentation

import android.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.release.inspectionapp.utils.NavAction
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun showDialog(content: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(content)
        dialogBuilder.setCancelable(true)
        val alert = dialogBuilder.create()
        alert.show()
    }

    fun navigate(navAction: NavAction) {
        when (navAction) {
            is NavAction.Forward -> {
                findNavController().navigate(navAction.navigate)
            }
            is NavAction.ChangeGraph -> {
                findNavController().setGraph(navAction.setGraph)
            }
            is NavAction.ForwardWithBundle -> {
                val bundle = bundleOf(
                    "bundle" to navAction.bundle
                )
                findNavController().navigate(navAction.navigate, bundle)
            }
        }
    }
}
