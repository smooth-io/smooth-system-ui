package io.getsmooth.kt.android.system_ui

import android.app.Activity
import android.view.View
import io.getsmooth.kt.android.system_ui.SystemUiHelper.Companion.LEVEL_IMMERSIVE
import io.getsmooth.kt.android.system_ui.SystemUiHelper.Companion.LEVEL_NORMAL

interface ScreenMode<T : ScreenMode<T>> {

    fun enable(delayMills: Long = 0): T

    fun disable(): T

    fun build(): SystemUiHelper

    fun listen(listener: SystemUiHelper.OnVisibilityChangeListener)

    fun removeListener(): T

}

abstract class BaseScreenMode<T : ScreenMode<T>>(
    val activity: Activity,
    val view: View?
) : ScreenMode<T> {

    private var systemUiHelper: SystemUiHelper? = null

    override fun enable(delayMills: Long): T {
        build().hide(delayMills)
        return instance()
    }

    override fun disable(): T {
        build().show()
        return instance()
    }

    override fun build(): SystemUiHelper {
        if (systemUiHelper == null) {
            systemUiHelper = buildInternal()
        }
        return systemUiHelper!!
    }

    override fun listen(listener: SystemUiHelper.OnVisibilityChangeListener) {
        build().listener(listener)
    }

    override fun removeListener(): T {
        build().removeListener()
        return instance()
    }

    protected abstract fun instance(): T

    protected abstract fun buildInternal(): SystemUiHelper

}

class NormalMode(activity: Activity, view: View?) : BaseScreenMode<NormalMode>(activity, view) {

    override fun instance(): NormalMode = this

    override fun buildInternal(): SystemUiHelper =
        SystemUiHelper(
            activity,
            view,
            LEVEL_NORMAL,
            0,
            true
        )
}


class LowProfile(activity: Activity, view: View?) : BaseScreenMode<LowProfile>(activity, view) {

    private var syncActionBar = true

    fun keepActionBar(): LowProfile {
        syncActionBar = false
        return this
    }

    override fun buildInternal(): SystemUiHelper =
        SystemUiHelper(
            activity,
            view,
            SystemUiHelper.LEVEL_LOW_PROFILE,
            0,
            syncActionBar
        )

    override fun instance(): LowProfile = this

}

class FullScreen(activity: Activity, view: View?) : BaseScreenMode<FullScreen>(activity, view) {

    override fun instance(): FullScreen = this

    private var syncActionBar = true

    fun keepActionBar(): FullScreen {
        syncActionBar = false
        return this
    }

    private var keepLayout = false
    fun keepLayout(): FullScreen {
        keepLayout = true
        return this
    }

    private var hideNavBar = true

    fun hideNavigationBar(): FullScreen {
        hideNavBar = true
        return this
    }


    override fun buildInternal(): SystemUiHelper =
        SystemUiHelper(
            activity,
            view,
            if (hideNavBar) SystemUiHelper.LEVEL_LEAN_BACK
            else SystemUiHelper.LEVEL_HIDE_STATUS_BAR,
            0,
            syncActionBar,
            keepLayout
        )


}


class ImmersiveMode(activity: Activity, view: View?) : BaseScreenMode<ImmersiveMode>(activity, view) {

    override fun instance(): ImmersiveMode = this

    private var syncActionBar = true

    fun keepActionBar(): ImmersiveMode {
        syncActionBar = false
        return this
    }

    private var keepLayout = false
    fun keepLayout(): ImmersiveMode {
        keepLayout = true
        return this
    }

    private var isSticky = false

    fun sticky(): ImmersiveMode {
        isSticky = true
        return this
    }


    override fun buildInternal(): SystemUiHelper =
        SystemUiHelper(
            activity,
            view,
            SystemUiHelper.LEVEL_IMMERSIVE,
            if (isSticky) SystemUiHelper.FLAG_IMMERSIVE_STICKY
            else 0,
            syncActionBar,
            keepLayout
        )

}