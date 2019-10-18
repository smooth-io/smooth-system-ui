package io.getsmooth.kt.android.system_ui

import android.app.Activity


fun Activity.normal(
    delay: Long = 0,
    block: NormalMode.() -> Unit
): NormalMode =
    normal(this, null, delay, block)


fun Activity.lowProfile(
    delay: Long = 0,
    block: LowProfile.() -> Unit
): LowProfile = lowProfile(this, null, delay, block)


fun Activity.fullScreen(
    delay: Long = 0,
    block: FullScreen.() -> Unit
): FullScreen = fullScreen(this, null, delay, block)

fun Activity.immersive(
    delay: Long = 0,
    block: ImmersiveMode.() -> Unit
): ImmersiveMode =
    immersive(this, null, delay, block)