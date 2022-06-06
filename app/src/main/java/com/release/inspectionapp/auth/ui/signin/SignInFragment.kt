package com.release.inspectionapp.auth.ui.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.release.core_di.component.CoreInjectHelper
import com.release.inspectionapp.auth.di.component.DaggerSignInComponent
import com.release.inspectionapp.auth.ui.signin.mvi.Signin
import com.release.inspectionapp.auth.ui.signin.mvi.SigninIntent
import com.release.inspectionapp.auth.ui.signin.mvi.SigninState
import com.release.inspectionapp.databinding.FragmentSigninBinding
import com.release.inspectionapp.utils.livedata.observeEvent
import com.release.inspectionapp.utils.mvi.IView
import com.release.inspectionapp.utils.presentation.BaseFragment
import kotlinx.coroutines.launch

class SignInFragment : BaseFragment(), IView<SigninState> {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        DaggerSignInComponent
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
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.btnSignin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intents.send(
                    SigninIntent.SigninUser(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                )
            }
        }

        binding.btnGoToSignUp.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intents.send(SigninIntent.SignupNewUser)
            }
        }

        viewModel.navigationEvent.observeEvent(viewLifecycleOwner) {
            navigate(it)
        }
    }

    override fun render(state: SigninState) {
        when (state.signin) {
            is Signin.Loading -> {}
            is Signin.UserSignedIn -> navigate(state.signin.navId)
            is Signin.ErrorMessage -> showDialog(state.signin.msg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
