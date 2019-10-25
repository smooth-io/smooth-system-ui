package io.getsmooth.kt.android.system_ui.helper

import android.app.Activity
import android.os.Handler
import android.view.View
import android.view.WindowManager

/**
 * Base implementation. Used on API level 10 and below.
 */
class SystemUiHelperImplBelowHC(
    handler: Handler,
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    showActionBar: Boolean,
    keepLayout: Boolean,
    autoDelay: Long
) : SystemUiHelperBaseImpl(handler,activity, view, level, flags, showActionBar, keepLayout,autoDelay) {

    init {
        if (flags and SystemUiHelper.FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES != 0) {
            activity.window.addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    override fun disableInternal() {
        if (level > SystemUiHelper.LEVEL_LOW_PROFILE) {
            //remove full screen
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    override fun enableInternal() {
        if (level <= SystemUiHelper.LEVEL_NORMAL) {
            //remove full screen
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else if (level > SystemUiHelper.LEVEL_LOW_PROFILE) {
            //make full screen
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}