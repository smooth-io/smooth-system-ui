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

package io.getsmooth.kt.android.system_ui

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
internal open class SystemUiHelperImplJB(
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    syncActionBar: Boolean,
    keepLayout: Boolean
) : SystemUiHelperImplICS(activity, view, level, flags, syncActionBar, keepLayout) {

    private fun buildActionBarFlags(): Int = 0
//        if (syncActionBar) View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        else 0

    private fun buildKeepLayoutFullScreenFlags(): Int =
        if (!keepLayout) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        else 0

    private fun buildKeepLayoutNavigationFlags(): Int =
        if (!keepLayout) View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        else 0

    override fun createShowFlags(): Int =
        (View.SYSTEM_UI_FLAG_VISIBLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

    override fun createHideFlags(): Int {
        var flag = super.createHideFlags()
        //sync layout -> we apply Layout_FULL_SCREEN, LAYOUT_HIDE_NAV
        //sync action bar -> we apply stable layout

        if (level >= SystemUiHelper.LEVEL_HIDE_STATUS_BAR) {
            flag = flag or (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or buildActionBarFlags()
                            or buildKeepLayoutFullScreenFlags()
                    )

            if (level >= SystemUiHelper.LEVEL_LEAN_BACK) {
                flag = flag or buildKeepLayoutNavigationFlags()
            }
        }

        return flag
    }

    override fun onSystemUiShown() {
        if (level >= SystemUiHelper.LEVEL_NORMAL) {
            // Manually show the action bar when in all modes.
            if (syncActionBar) {
                if (activity is AppCompatActivity) {
                    activity.supportActionBar?.show()
                    getActionBar(activity.window.decorView)?.visibility = View.VISIBLE
                } else {
                    activity.actionBar?.show()
                    getActionBar(activity.window.decorView)?.visibility = View.VISIBLE
                }
            }
        }

        this.isShowing = true
    }

    override fun onSystemUiHidden() {
//        activity.window?.decorView?.requestLayout()
        if (level >= SystemUiHelper.LEVEL_NORMAL) {
            // Manually hide the action bar when in low profile mode.
            if (syncActionBar) {
                if (activity is AppCompatActivity) {
                    activity.supportActionBar?.hide()
                    getActionBar(activity.window.decorView)?.visibility = View.GONE
                } else {
                    activity.actionBar?.hide()
                    getActionBar(activity.window.decorView)?.visibility = View.GONE
                }
            }
        }

        this.isShowing = false
    }

    fun getActionBar(view: View): ViewGroup? {
        try {
            if (view is ViewGroup) {
                val viewGroup = view

                if (viewGroup is Toolbar) {
                    return viewGroup
                }

                for (i in 0 until viewGroup.childCount) {
                    val actionBar = getActionBar(viewGroup.getChildAt(i))

                    if (actionBar != null) {
                        return actionBar
                    }
                }
            }
        } catch (e: Exception) {
        }

        return null
    }

}
