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

@TargetApi(Build.VERSION_CODES.M)
internal class SystemUiHelperImplM(
    handler: Handler,
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    showActionBar: Boolean,
    keepLayout: Boolean,
    autoDelay: Long
) : SystemUiHelperImplKK(handler, activity, view, level, flags, showActionBar, keepLayout, autoDelay) {

    override fun createEnableFlags(): Int = when (statusBarIconsColor) {
        StatusBarIconsColor.NOT_SPECIFIED -> super.createEnableFlags()
        StatusBarIconsColor.LIGHT -> super.createEnableFlags() or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else -> {
            if (isStatusBarIconsDark()) {
                super.createEnableFlags()
            } else {
                super.createEnableFlags() xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }


    override fun createDisableFlags(): Int = when (statusBarIconsColor) {
        StatusBarIconsColor.LIGHT -> super.createDisableFlags() xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else -> super.createDisableFlags()
    }

    private fun isStatusBarIconsDark(): Boolean =
        view.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == 0

}
