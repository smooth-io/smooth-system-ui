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

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
internal open class SystemUiHelperImplICS(
    handler: Handler,
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    showActionBar: Boolean,
    keepLayout: Boolean,
    autoDelay: Long
) : SystemUiHelperImplHC(handler,activity, view, level, flags, showActionBar, keepLayout,autoDelay) {

    override fun createDisableFlags(): Int = 0

    // Intentionally override test flags.
    override fun createVisibilityTestFlags(): Int =
        if (level >= SystemUiHelper.LEVEL_LEAN_BACK) {
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        } else View.SYSTEM_UI_FLAG_LOW_PROFILE


    override fun createEnableFlags(): Int {
        if (level <= SystemUiHelper.LEVEL_NORMAL) return 0
        var flag = View.SYSTEM_UI_FLAG_LOW_PROFILE

        if (level >= SystemUiHelper.LEVEL_LEAN_BACK) {
            flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        return flag
    }

}

