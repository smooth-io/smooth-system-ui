package io.getsmooth.kt.android.system_ui

import android.app.Activity
import android.view.View
import io.getsmooth.kt.android.system_ui.helper.StatusBarIconsColor
import io.getsmooth.kt.android.system_ui.helper.SystemUiHelper
import io.getsmooth.kt.android.system_ui.helper.SystemUiHelper.Companion.LEVEL_NORMAL


interface ScreenMode<T : ScreenMode<T>> {

    fun enable(delayMills: Long = 0): T

    fun disable(delayMills: Long = 0): T

    fun build(): SystemUiHelper

    fun listen(listener: SystemUiHelper.OnVisibilityChangeListener): T

    fun listen(block: (Boolean, Boolean) -> Unit): T

    fun removeListener(): T

    fun release(): T

    fun autoDelay(autoDelayMills: Long = 0): T

    fun lightStatusBar(): T

    fun darkStatusBar(): T

}

abstract class BaseScreenMode<T : ScreenMode<T>>(
    val activity: Activity,
    val view: View?
) : ScreenMode<T> {

    private var systemUiHelper: SystemUiHelper? = null

    override fun enable(delayMills: Long): T {
        build().enable(delayMills)
        return instance()
    }

    override fun disable(delayMills: Long): T {
        build().disable(delayMills)
        return instance()
    }

    override fun build(): SystemUiHelper {
        if (systemUiHelper == null) {
            systemUiHelper = buildInternal()
        }
        return systemUiHelper!!
    }

    override fun listen(listener: SystemUiHelper.OnVisibilityChangeListener): T {
        build().listener(listener)
        return instance()
    }

    override fun listen(block: (Boolean, Boolean) -> Unit): T {
        listen(object : SystemUiHelper.OnVisibilityChangeListener {
            override fun onVisibilityChange(systemUiVisible: Boolean, isEnabled: Boolean) {
                block(systemUiVisible, isEnabled)
            }
        })
        return instance()
    }

    override fun removeListener(): T {
        build().removeListener()
        return instance()
    }

    override fun release(): T {
        build().release()
        return instance()
    }

    protected abstract fun instance(): T

    protected abstract fun buildInternal(): SystemUiHelper

    override fun autoDelay(autoDelayMills: Long): T {
        build().autoDelay = autoDelayMills
        return instance()
    }

    override fun lightStatusBar(): T {
        build().statusBarIconsColor = StatusBarIconsColor.LIGHT
        return instance()
    }

    override fun darkStatusBar(): T {
        build().statusBarIconsColor = StatusBarIconsColor.DARK
        return instance()
    }

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

    private var showActionBar = false

    fun showActionBar(): LowProfile {
        showActionBar = true
        return this
    }

    override fun buildInternal(): SystemUiHelper =
        SystemUiHelper(
            activity,
            view,
            SystemUiHelper.LEVEL_LOW_PROFILE,
            0,
            showActionBar
        )

    override fun instance(): LowProfile = this

}

class FullScreen(activity: Activity, view: View?) : BaseScreenMode<FullScreen>(activity, view) {

    override fun instance(): FullScreen = this

    private var showActionBar = false

    fun showActionBar(): FullScreen {
        showActionBar = true
        return this
    }

    private var keepLayout = false
    fun keepLayout(): FullScreen {
        keepLayout = true
        return this
    }

    private var hideNavBar = true
    fun showNavigationBar(): FullScreen {
        hideNavBar = false
        return this
    }

    override fun buildInternal(): SystemUiHelper =
        SystemUiHelper(
            activity,
            view,
            if (hideNavBar) SystemUiHelper.LEVEL_LEAN_BACK
            else SystemUiHelper.LEVEL_HIDE_STATUS_BAR,
            0,
            showActionBar,
            keepLayout
        )


}


class ImmersiveMode(activity: Activity, view: View?) : BaseScreenMode<ImmersiveMode>(activity, view) {

    override fun instance(): ImmersiveMode = this

    private var showActionBar = false

    fun showActionBar(): ImmersiveMode {
        showActionBar = true
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
            showActionBar,
            keepLayout
        )

}