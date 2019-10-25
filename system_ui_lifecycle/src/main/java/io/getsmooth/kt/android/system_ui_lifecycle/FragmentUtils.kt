package io.getsmooth.kt.android.system_ui_lifecycle

import androidx.fragment.app.Fragment
import io.getsmooth.kt.android.system_ui.*


fun Fragment.normalAuto(
    delay: Long = 0,
    block: NormalMode.() -> Unit
): NormalMode? = activity?.let {
    normal(it, view, -1, block).let {
        viewLifecycleOwner.lifecycle.addObserver(
            SystemUiLifecycleObserver(it,delay)
        )
        it
    }
}


fun Fragment.lowProfileAuto(
    delay: Long = 0,
    block: LowProfile.() -> Unit
): LowProfile? = activity?.let {
    lowProfile(
        it,
        view,
        -1,
        block
    ).let {
        viewLifecycleOwner.lifecycle.addObserver(
            SystemUiLifecycleObserver(it,delay)
        )
        it
    }
}


fun Fragment.fullScreenAuto(
    delay: Long = 0,
    block: FullScreen.() -> Unit
): FullScreen? = activity?.let {
    fullScreen(
        it,
        view,
        -1,
        block
    ).let {
        viewLifecycleOwner.lifecycle.addObserver(
            SystemUiLifecycleObserver(it,delay)
        )
        it
    }
}

fun Fragment.immersiveAuto(
    delay: Long = 0,
    block: ImmersiveMode.() -> Unit
): ImmersiveMode? = activity?.let {
    immersive(
        it,
        view,
        -1,
        block
    ).let {
        viewLifecycleOwner.lifecycle.addObserver(
            SystemUiLifecycleObserver(it,delay)
        )
        it
    }
}