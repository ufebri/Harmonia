package id.daydream.harmonia.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import febri.uray.bedboy.core.domain.usecase.auth.CheckUserLoggedInUseCase
import id.daydream.harmonia.databinding.ActivityOnboardingBinding
import id.daydream.harmonia.presentation.MainActivity
import id.daydream.harmonia.presentation.home.HomeActivity
import javax.inject.Inject


@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    @Inject
    lateinit var checkUserLoggedInUseCase: CheckUserLoggedInUseCase

    private val binding: ActivityOnboardingBinding by lazy {
        ActivityOnboardingBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContentView(binding.root)

        // Cek apakah user sudah login
        val isLoggedIn = checkUserLoggedInUseCase()
        if (isLoggedIn) {
            // Langsung ke MainActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            binding.apply {
                viewpager.adapter = CustomFragmentPagerAdapter(supportFragmentManager)
                viewpager.setCurrentItem(titleArray.count() * 10, false)
            }
        }
    }
}