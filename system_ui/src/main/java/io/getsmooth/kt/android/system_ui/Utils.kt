package io.getsmooth.kt.android.system_ui

import android.app.Activity
import android.view.View

fun normal(
    activity: Activity,
    view: View?,
    delay: Long = 0,
    block: NormalMode.() -> Unit
): NormalMode = NormalMode(activity, view).let {
    block(it)
    it.enable(delay)
}


fun lowProfile(
    activity: Activity,
    view: View?,
    delay: Long = 0,
    block: LowProfile.() -> Unit
): LowProfile = LowProfile(activity, view).let {
    block(it)
    it.enable(delay)
}


fun fullScreen(
    activity: Activity,
    view: View?,
    delay: Long = 0,
    block: FullScreen.() -> Unit
): FullScreen =
    FullScreen(activity, view).let {
        block(it)
        it.enable(delay)
    }


fun immersive(
    activity: Activity,
    view: View?,
    delay: Long = 0,
    block: ImmersiveMode.() -> Unit
): ImmersiveMode = ImmersiveMode(activity, view).let {
    block(it)
    it.enable(delay)
}
