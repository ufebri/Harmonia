package id.daydream.harmonia.presentation.onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import febri.uray.bedboy.core.util.collectWhenStarted
import id.daydream.harmonia.R
import id.daydream.harmonia.databinding.FragmentOnboardingBinding
import id.daydream.harmonia.presentation.analyze.AnalyzeActivity
import id.daydream.harmonia.presentation.home.HomeActivity
import javax.inject.Inject

private const val ARG_BACKGROUND_COLOR = "param1"
private const val ARG_RESOURCE = "param2"
private const val ARG_TITLE = "param3"

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding

    private var param1: Int? = null
    private var param2: Int? = null
    private var param3: String? = null

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account = task.result
                val idToken = account?.idToken
                if (!idToken.isNullOrEmpty()) {
                    viewModel.signInGoogle(idToken)
                } else {
                    // handle error
                    Toast.makeText(
                        requireContext(),
                        "ID Token: $idToken",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Google sign-in failed: ${task.result}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_BACKGROUND_COLOR)
            param2 = it.getInt(ARG_RESOURCE)
            param3 = it.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            root.setBackgroundColor(param1 ?: Color.RED)

            lottieAnimationView.apply {
                setAnimation(param2 ?: R.raw.loopcancer)
                repeatCount = LottieDrawable.INFINITE
                repeatMode = LottieDrawable.REVERSE
            }

            fragmentTextview.text = param3 ?: "Hello fellow developer!"


            signInButton.apply {
                if (titleArray[2] == param3) {
                    isVisible = true
                    setOnClickListener { signIn() }
                } else {
                    isGone = true
                }
            }
        }

        observeViewModel()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun observeViewModel() {
        // Observasi userState
        viewModel.userState.collectWhenStarted(requireActivity()) { user ->
            user?.let {
                // Sukses login
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        // Observasi errorState
        viewModel.errorState.collectWhenStarted(requireActivity()) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(
                    requireContext(),
                    "ID Token: $errorMsg",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int, param3: String) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_BACKGROUND_COLOR, param1)
                    putInt(ARG_RESOURCE, param2)
                    putString(ARG_TITLE, param3)
                }
            }
    }
}