package febri.uray.bedboy.core.util

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

inline fun <T> StateFlow<T>.collectWhenStarted(
    fragmentActivity: FragmentActivity,
    crossinline onCollect: (T) -> Unit
) {
    fragmentActivity.lifecycleScope.launch {
        fragmentActivity.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectWhenStarted.collect {
                onCollect(it)
            }
        }
    }
}
