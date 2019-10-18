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
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
internal open class SystemUiHelperImplHC(
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    syncActionBar: Boolean,
    keepLayout: Boolean
) : SystemUiHelperImpl(activity, view, level, flags, syncActionBar, keepLayout) {

//    private val decorView: View = activity.window.decorView

    private var desiredShow: Boolean = isShowing

    init {
        view.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and createTestFlags() != 0) {
                onSystemUiHidden()
                if (desiredShow) {
                    show()
                }
            } else {
                onSystemUiShown()
                if (!desiredShow) {
                    hide()
                }
            }
        }
    }

    override fun show() {
//        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//        Handler().postDelayed({
//            view.setLayerType(View.LAYER_TYPE_HARDWARE, null)
//        }, 200)
        desiredShow = true
        view.systemUiVisibility = createShowFlags()
    }

    override fun hide() {
        if (level == 0) {
            show()
        } else {
            desiredShow = false
            view.systemUiVisibility = createHideFlags()
        }
    }

    protected open fun onSystemUiShown() {
        if (syncActionBar) {
            if (activity is AppCompatActivity) {
                activity.supportActionBar?.show()
            } else {
                activity.actionBar?.show()
            }
        }

        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        this.isShowing = true
    }

    protected open fun onSystemUiHidden() {
        if (syncActionBar) {
            if (activity is AppCompatActivity) {
                activity.supportActionBar?.hide()
            } else {
                activity.actionBar?.hide()
            }
        }

        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        this.isShowing = false
    }

    protected open fun createShowFlags(): Int = 0

    protected open fun createHideFlags(): Int {
        return View.STATUS_BAR_HIDDEN
    }

    protected open fun createTestFlags(): Int {
        return View.STATUS_BAR_HIDDEN
    }

}
