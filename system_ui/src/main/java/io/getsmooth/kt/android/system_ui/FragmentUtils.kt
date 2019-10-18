package io.getsmooth.kt.android.system_ui

import androidx.fragment.app.Fragment


fun Fragment.normal(
    delay: Long = 0,
    block: NormalMode.() -> Unit
): NormalMode? = activity?.let {
    normal(it, view, delay, block)
}


fun Fragment.lowProfile(
    delay: Long = 0,
    block: LowProfile.() -> Unit
): LowProfile? = activity?.let {
    lowProfile(
        it,
        view,
        delay,
        block
    )
}


fun Fragment.fullScreen(
    delay: Long = 0,
    block: FullScreen.() -> Unit
): FullScreen? = activity?.let {
    fullScreen(
        it,
        view,
        delay,
        block
    )
}

fun Fragment.immersive(
    delay: Long = 0,
    block: ImmersiveMode.() -> Unit
): ImmersiveMode? = activity?.let {
    immersive(
        it,
        view,
        delay,
        block
    )
}