package id.daydream.harmonia.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.daydream.harmonia.R
import id.daydream.harmonia.databinding.FragmentProfileBinding
import id.daydream.harmonia.presentation.home.detail.DetailActivity
import id.daydream.harmonia.presentation.onboarding.OnboardingActivity

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCurrentUser()

        /* Menu */
        val PRIVACY_MENU = 1
        val VERSION_APP = 2
        val SIGN_OUT = 3

        val menuAction: List<Pair<Int, String>> =
            listOf(
                Pair(PRIVACY_MENU, "Privacy Policy"),
                Pair(VERSION_APP, "Version v1.0"),
                Pair(SIGN_OUT, "Sign Out")
            )

        binding?.apply {

            rvTools.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                val mAdapter = ProfileAdapter { mMenu ->
                    when (mMenu) {
                        PRIVACY_MENU -> goToPrivacyPolicy()
                        VERSION_APP -> {}
                        SIGN_OUT -> logout()
                    }
                }
                adapter = mAdapter
                mAdapter.submitList(menuAction)
            }

            viewModel.user.observe(viewLifecycleOwner) { user ->
                Glide.with(requireActivity())
                    .load(
                        user?.urlPhoto ?: ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.baseline_broken_image_24
                        )
                    )
                    .into(sivPhoto)
                tvName.text = user?.displayName ?: "User"
            }
        }
    }

    private fun logout() {
        viewModel.logout()
        val intent = Intent(context, OnboardingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        requireActivity().startActivity(intent)
    }

    private fun goToPrivacyPolicy() {
        startActivity(
            Intent(
                requireActivity(),
                DetailActivity::class.java
            ).putExtra("url", getString(R.string.privacy_policy_links))
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}