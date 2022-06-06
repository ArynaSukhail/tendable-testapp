package com.release.inspectionapp.auth.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.release.core_di.component.CoreInjectHelper
import com.release.inspectionapp.auth.di.component.DaggerSignUpComponent
import com.release.inspectionapp.auth.ui.signup.mvi.SignUp
import com.release.inspectionapp.auth.ui.signup.mvi.SignUpIntent
import com.release.inspectionapp.auth.ui.signup.mvi.SignUpState
import com.release.inspectionapp.databinding.FragmentSignUpBinding
import com.release.inspectionapp.utils.mvi.IView
import com.release.inspectionapp.utils.presentation.BaseFragment
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment(), IView<SignUpState> {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        DaggerSignUpComponent
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.btnSignup.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intents.send(
                    SignUpIntent.SignUpUser(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                )
            }
        }
    }

    override fun render(state: SignUpState) {
        when (state.signup) {
            is SignUp.Loading -> {}
            is SignUp.UserSignedUp -> navigate(state.signup.navId)
            is SignUp.ErrorMessage -> showDialog(state.signup.msg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}