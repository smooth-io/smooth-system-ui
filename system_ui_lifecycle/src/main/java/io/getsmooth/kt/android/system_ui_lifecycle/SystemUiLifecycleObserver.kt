package io.getsmooth.kt.android.system_ui_lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.getsmooth.kt.android.system_ui.ScreenMode

class SystemUiLifecycleObserver<Mode : ScreenMode<Mode>>(
    val mode: Mode,
    val enableDelay: Long = 0
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onViewCreated() {
        mode.enable(enableDelay)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onViewDestroyed() {
        mode.disable()
    }

}