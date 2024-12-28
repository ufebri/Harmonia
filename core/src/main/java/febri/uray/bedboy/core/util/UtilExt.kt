package febri.uray.bedboy.core.util

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow

inline fun <T> StateFlow<T>.collectWhenStarted(
    fragmentActivity: FragmentActivity,
    crossinline onCollect: (T) -> Unit
) {
    fragmentActivity.lifecycleScope.launchWhenStarted {
        this@collectWhenStarted.collect {
            onCollect(it)
        }
    }
}