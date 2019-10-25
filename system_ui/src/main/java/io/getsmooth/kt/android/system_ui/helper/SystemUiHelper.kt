/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getsmooth.kt.android.system_ui.helper

import android.app.Activity
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager

/**
 * Helper for controlling the visibility of the System UI across the various API levels. To use
 * this API, instantiate an instance of this class with the required level. The level specifies the
 * extent to which the System UI's visibility is changed when you call [.enable]
 * or [.toggle].
 * @param activity The Activity who's system UI should be changed
 * @param level    The level of hiding. Should be either [.LEVEL_LOW_PROFILE],
 * [.LEVEL_HIDE_STATUS_BAR], [.LEVEL_LEAN_BACK] or
 * [.LEVEL_IMMERSIVE]
 * @param flags    Additional options. See [.FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES] and
 * [.FLAG_IMMERSIVE_STICKY]
 * @param listener A listener which is called when the system visibility is changed
 */
class SystemUiHelper(
    val activity: Activity,
    view: View?,
    level: Int,
    flags: Int,
    showActionBar: Boolean = false,
    keepLayout: Boolean = false,
    autoDelay: Long = 1000L
) {

    internal var statusBarIconsColor: StatusBarIconsColor = StatusBarIconsColor.NOT_SPECIFIED
        set(value) {
            field = value
            impl.statusBarIconsColor = value
        }

    internal var autoDelay: Long = autoDelay
        set(value) {
            field = value
            impl.autoDelay = value
        }

    private val impl: SystemUiHelperBaseImpl

    private val handler: Handler = Handler(Looper.getMainLooper())

    init {

        val myView = view ?: activity.window.decorView

        // Create impl
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> impl =
                SystemUiHelperImplM(
                    handler,
                    activity,
                    myView,
                    level,
                    flags,
                    showActionBar,
                    keepLayout,
                    autoDelay
                )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> impl =
                SystemUiHelperImplKK(
                    handler,
                    activity,
                    myView,
                    level,
                    flags,
                    showActionBar,
                    keepLayout,
                    autoDelay
                )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN -> impl =
                SystemUiHelperImplJB(
                    handler,
                    activity,
                    myView,
                    level,
                    flags,
                    showActionBar,
                    keepLayout,
                    autoDelay
                )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH -> impl =
                SystemUiHelperImplICS(
                    handler,
                    activity,
                    myView,
                    level,
                    flags,
                    showActionBar,
                    keepLayout,
                    autoDelay
                )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB -> impl =
                SystemUiHelperImplHC(
                    handler,
                    activity,
                    myView,
                    level,
                    flags,
                    showActionBar,
                    keepLayout,
                    autoDelay
                )
            else -> impl =
                SystemUiHelperImplBelowHC(
                    handler,
                    activity,
                    myView,
                    level,
                    flags,
                    showActionBar,
                    keepLayout,
                    autoDelay
                )
        }
    }

    fun disable(mills: Long = 0) {
        impl.disable(mills)
    }

    fun enable(mills: Long = 0) {
        impl.enable(mills)
    }

    fun release() {
        impl.release()
    }

    fun listener(listener: OnVisibilityChangeListener) {
        impl.listener(listener)
    }

    fun removeListener() {
        impl.removeListener()
    }


    /**
     * A callback interface used to listen for system UI visibility changes.
     */
    interface OnVisibilityChangeListener {
        /**
         * Called when the system UI visibility has changed.
         *
         * @param systemUiVisible True if the system UI is systemUiVisible.
         */
        fun onVisibilityChange(systemUiVisible: Boolean, isEnabled: Boolean)
    }


    companion object {

        val LEVEL_NORMAL = 0
        /**
         * In this level, the helper will toggle low profile mode.
         */
        val LEVEL_LOW_PROFILE = 1

        /**
         * In this level, the helper will toggle the visibility of the status bar.
         * If there is a navigation bar, it will toggle low profile mode.
         */
        val LEVEL_HIDE_STATUS_BAR = 2

        /**
         * In this level, the helper will toggle the visibility of the navigation bar
         * (if present and if possible) and status bar. In cases where the navigation
         * bar is present but cannot be hidden, it will toggle low profile mode.
         */
        val LEVEL_LEAN_BACK = 3

        /**
         * In this level, the helper will toggle the visibility of the navigation bar
         * (if present and if possible) and status bar, in an immersive mode. This means that the app
         * will continue to receive all touch events. The user can reveal the system bars with an
         * inward swipe along the region where the system bars normally appear.
         *
         *
         * The [.FLAG_IMMERSIVE_STICKY] flag can be used to control how the system bars are
         * displayed.
         */
        val LEVEL_IMMERSIVE = 4

        /**
         * When this flag is set, the
         * [WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN]
         * flag will be set on older devices, making the status bar "float" on top
         * of the activity layout. This is most useful when there are no controls at
         * the top of the activity layout.
         *
         *
         * This flag isn't used on newer devices because the [action
 * bar](http://developer.android.com/design/patterns/actionbar.html), the most important structural element of an Android app, should
         * be visible and not obscured by the system UI.
         */
        val FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES = 0x1

        /**
         * Used with [.LEVEL_IMMERSIVE]. When this flag is set, an inward swipe in the system
         * bars areas will cause the system bars to temporarily appear in a semi-transparent state,
         * but no flags are cleared, and your system UI visibility change listeners are not triggered.
         * The bars automatically enable again after a short delay, or if the user interacts with the
         * middle of the screen.
         */
        val FLAG_IMMERSIVE_STICKY = 0x2
    }

}