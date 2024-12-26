package id.daydream.harmonia.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.daydream.harmonia.R
import id.daydream.harmonia.databinding.ActivityHomeBinding
import id.daydream.harmonia.presentation.MainActivity
import id.daydream.harmonia.presentation.adapter.LoadingStateAdapter
import id.daydream.harmonia.presentation.adapter.NewsAdapter
import id.daydream.harmonia.presentation.detail.DetailActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showContent()
    }

    private fun showContent() {
        lifecycleScope.launch {
            showLoading(true)
            showNotFound(false)
            delay(3000L)

            viewModel.allNews.observe(this@HomeActivity) { result ->
                if (result != null) {
                    binding.apply {

                        rvNews.apply {
                            layoutManager = LinearLayoutManager(this@HomeActivity)
                            val mAdapter = NewsAdapter(onClick = { onClickData ->
                                startActivity(
                                    Intent(
                                        this@HomeActivity,
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

                        fabScan.apply {
                            fabScan.isVisible = true
                            setOnClickListener {
                                startActivity(
                                    Intent(
                                        this@HomeActivity,
                                        MainActivity::class.java
                                    )
                                )
                            }
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
        binding.apply {
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
        binding.lottieNotFound.isVisible = state
    }
}