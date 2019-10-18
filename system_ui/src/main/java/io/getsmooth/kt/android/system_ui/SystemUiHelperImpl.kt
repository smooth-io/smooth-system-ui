package io.getsmooth.kt.android.system_ui

import android.app.Activity
import android.view.View


/**
 * Construct a new SystemUiHelper.
 *
 * @param activity The Activity who's system UI should be changed
 * @param level    The level of hiding. Should be either [.LEVEL_LOW_PROFILE],
 * [.LEVEL_HIDE_STATUS_BAR], [.LEVEL_LEAN_BACK] or
 * [.LEVEL_IMMERSIVE]
 * @param flags    Additional options. See [.FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES] and
 * [.FLAG_IMMERSIVE_STICKY]
 */
abstract class SystemUiHelperImpl(
    val activity: Activity,
    val view: View,
    val level: Int, val flags: Int,
    val syncActionBar: Boolean,
    val keepLayout: Boolean
) {

    internal var onVisibilityChangeListener: SystemUiHelper.OnVisibilityChangeListener? = null

    var isShowing: Boolean = true
        set(value) {
            field = value
            onVisibilityChangeListener?.onVisibilityChange(this.isShowing)
        }

    internal abstract fun show()

    internal abstract fun hide()
}
