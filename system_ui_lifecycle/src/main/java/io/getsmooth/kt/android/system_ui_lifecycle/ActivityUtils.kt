package io.getsmooth.kt.android.system_ui_lifecycle

import androidx.appcompat.app.AppCompatActivity
import io.getsmooth.kt.android.system_ui.*
import io.getsmooth.kt.android.system_ui.FullScreen
import io.getsmooth.kt.android.system_ui.ImmersiveMode
import io.getsmooth.kt.android.system_ui.LowProfile
import io.getsmooth.kt.android.system_ui.NormalMode


fun AppCompatActivity.normalAuto(
    delay: Long = 0,
    block: NormalMode.() -> Unit
): NormalMode =
    normal(this, null,delay,block).let {
        lifecycle.addObserver(
            SystemUiLifecycleObserver(it)
        )
        it
    }


fun AppCompatActivity.lowProfileAuto(
    delay: Long = 0,
    block: LowProfile.() -> Unit
): LowProfile = lowProfile(this, null, delay, block).let {
    lifecycle.addObserver(
        SystemUiLifecycleObserver(it)
    )
    it
}


fun AppCompatActivity.fullScreenAuto(
    delay: Long = 0,
    block: FullScreen.() -> Unit
): FullScreen = fullScreen(this, null, delay, block).let {
    lifecycle.addObserver(
        SystemUiLifecycleObserver(it)
    )
    it
}

fun AppCompatActivity.immersiveAuto(
    delay: Long = 0,
    block: ImmersiveMode.() -> Unit
): ImmersiveMode =
    immersive(this, null, delay, block).let {
        lifecycle.addObserver(
            SystemUiLifecycleObserver(it)
        )
        it
    }