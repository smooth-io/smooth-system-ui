package io.getsmooth.kt.android.system_ui

import android.app.Activity
import android.view.View
import android.view.WindowManager

/**
 * Base implementation. Used on API level 10 and below.
 */
class SystemUiHelperImplBelowHC(
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    syncActionBar: Boolean,
    keepLayout: Boolean
) : SystemUiHelperImpl(activity, view, level, flags, syncActionBar, keepLayout) {

    init {
        if (flags and SystemUiHelper.FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES != 0) {
            activity.window.addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    override fun show() {
        if (level > SystemUiHelper.LEVEL_LOW_PROFILE) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            this.isShowing = true
        }
    }

    override fun hide() {
        if (level == 0) show()
        else if (level > SystemUiHelper.LEVEL_LOW_PROFILE) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            this.isShowing = false
        }
    }
}