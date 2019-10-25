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

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Handler
import android.view.View

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
internal open class SystemUiHelperImplHC(
    handler: Handler,
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    showActionBar: Boolean,
    keepLayout: Boolean,
    autoDelay: Long
) : SystemUiHelperBaseImpl(handler, activity, view, level, flags, showActionBar, keepLayout, autoDelay) {

    init {
        view.setOnSystemUiVisibilityChangeListener { visibility ->
            val isUiVisible = visibility and createVisibilityTestFlags() == 0
            if (!isUiInSync(
                    level, desiredEnable, isUiVisible
                )
            ) {
                isEnabled = false
                syncUi(desiredEnable, isEnabled)
            }

        }
    }


    override fun disableInternal() {
        view.systemUiVisibility = createDisableFlags()
        onDisabled()
    }

    override fun enableInternal() {
        view.systemUiVisibility = createEnableFlags()
        onEnabled()
    }

    protected open fun onDisabled() {
        if (showActionBar) {
            hideActionBar()
        } else {
            showActionBar()
        }
    }

    protected open fun onEnabled() {
        if (showActionBar) {
            showActionBar()
        } else {
            hideActionBar()
        }
    }

    protected open fun createDisableFlags(): Int = 0

    protected open fun createEnableFlags(): Int =
        if (level <= SystemUiHelper.LEVEL_NORMAL) 0
        else View.STATUS_BAR_HIDDEN


    protected open fun createVisibilityTestFlags(): Int {
        return View.STATUS_BAR_HIDDEN
    }

    //disable means show
    //enable means hide

}
