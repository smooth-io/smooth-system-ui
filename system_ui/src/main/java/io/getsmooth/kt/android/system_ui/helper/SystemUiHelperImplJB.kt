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


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
internal open class SystemUiHelperImplJB(
    handler: Handler,
    activity: Activity,
    view: View,
    level: Int, flags: Int,
    showActionBar: Boolean,
    keepLayout: Boolean,
    autoDelay: Long
) : SystemUiHelperImplICS(handler, activity, view, level, flags, showActionBar, keepLayout, autoDelay) {

    private fun buildActionBarFlags(): Int = 0

    override fun createVisibilityTestFlags(): Int =
        if (level >= SystemUiHelper.LEVEL_HIDE_STATUS_BAR)
            View.SYSTEM_UI_FLAG_FULLSCREEN
        else super.createVisibilityTestFlags()

    private fun buildKeepLayoutFullScreenFlags(): Int =
        if (keepLayout) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        else 0

    private fun buildKeepLayoutNavigationFlags(): Int =
        if (keepLayout) View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        else 0

    override fun createDisableFlags(): Int = 0
//        (View.SYSTEM_UI_FLAG_VISIBLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

    override fun createEnableFlags(): Int {
        var flag = super.createEnableFlags()

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


}
