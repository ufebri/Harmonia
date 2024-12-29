package id.daydream.harmonia.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.daydream.harmonia.databinding.FragmentHomeBinding
import id.daydream.harmonia.presentation.home.adapter.LoadingStateAdapter
import id.daydream.harmonia.presentation.home.adapter.NewsAdapter
import id.daydream.harmonia.presentation.home.detail.DetailActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            showLoading(true)
            showNotFound(false)
            delay(3000L)

            viewModel.allNews.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    binding?.apply {

                        rvNews.apply {
                            layoutManager = LinearLayoutManager(requireActivity())
                            val mAdapter = NewsAdapter(onClick = { onClickData ->
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        DetailActivity::class.java
                                    ).putExtra("url", onClickData.url)
                                )
                            }, length = { length -> showNotFound(length < 1) })

                            adapter =
                                mAdapter.withLoadStateHeaderAndFooter(footer = LoadingStateAdapter {
                                    mAdapter.retry()
                                }, header = LoadingStateAdapter { mAdapter.retry() })

                            mAdapter.submitData(lifecycle, result)
                            showLoading(false)
                        }
                    }
                } else {
                    showLoading(false)
                    showNotFound(true)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            if (state) {
                progress.isVisible = true
                rvNews.isGone = true
            } else {
                progress.isGone = true
                rvNews.isVisible = true
            }
        }
    }

    private fun showNotFound(state: Boolean) {
        binding?.lottieNotFound?.isVisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}