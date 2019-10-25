package io.getsmooth.kt.android.system_ui.helper

import android.app.Activity
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat


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
abstract class SystemUiHelperBaseImpl(
    val handler: Handler,
    val activity: Activity,
    val view: View,
    val level: Int, val flags: Int,
    val showActionBar: Boolean,
    val keepLayout: Boolean,
    var autoDelay: Long
) {

    internal var statusBarIconsColor: StatusBarIconsColor = StatusBarIconsColor.NOT_SPECIFIED

    private val containerView: View? = activity?.findViewById<ViewGroup>(android.R.id.content).let {
        if (it.childCount >= 1) {
            it.getChildAt(0)
        } else null
    }

    private val enableRunnable: Runnable = Runnable { enable() }

    private val disableRunnable = Runnable { disable() }

    private fun removeEnableQueuedRunnable() {
        // Ensure that any currently queued enable calls are removed
        handler.removeCallbacks(enableRunnable)
    }

    private fun removeDisableQueuedRunnable() {
        // Ensure that any currently queued disable calls are removed
        handler.removeCallbacks(disableRunnable)
    }

    protected var desiredEnable: Boolean = false

    internal var onVisibilityChangeListener: SystemUiHelper.OnVisibilityChangeListener? = null
    var isEnabled: Boolean = false
        set(value) {
            field = value
            onVisibilityChangeListener?.onVisibilityChange(isSystemUiVisible(), value)
        }

    internal final fun disable(mills: Long = 0L) {
        when {
            mills < 0 -> return
            mills > 0 -> {
                delayDisable(mills)
                return
            }
            else -> {
                desiredEnable = false
                removeDisableQueuedRunnable()
                disableInternal()
                stopListeningForInsets()
                isEnabled = false
            }
        }


    }

    internal final fun enable(mills: Long = 0L) {
        when {
            mills < 0 -> return
            mills > 0 -> {
                delayEnable(mills)
                return
            }
            else -> {
                desiredEnable = true
                listenForInsets()
                removeEnableQueuedRunnable()
                enableInternal()
                isEnabled = true
            }
        }
    }

    private fun delayEnable(delayMillis: Long) {
        // Ensure that any currently queued enable calls are removed
        removeEnableQueuedRunnable()
        handler.postDelayed(enableRunnable, delayMillis)
    }

    private fun delayDisable(delayMillis: Long) {
        // Ensure that any currently queued enable calls are removed
        removeDisableQueuedRunnable()
        handler.postDelayed(disableRunnable, delayMillis)
    }


    internal abstract fun disableInternal()

    internal abstract fun enableInternal()

    protected fun hideActionBar() {
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.hide()
        } else {
            activity.actionBar?.hide()
        }
    }

    protected fun showActionBar() {
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.show()
        } else {
            activity.actionBar?.show()
        }
    }

    protected fun isSystemUiVisible(): Boolean =
        if (level <= SystemUiHelper.LEVEL_NORMAL) true
        else !isEnabled


    protected final fun isEnabledInternal(): Boolean =
        if (level <= SystemUiHelper.LEVEL_NORMAL) isSystemUiVisible()
        else !isSystemUiVisible()

    protected fun isUiInSync(
        level: Int,
        desiredEnable: Boolean,
        isUiVisible: Boolean
    ): Boolean =
        if (level <= SystemUiHelper.LEVEL_NORMAL) desiredEnable && isUiVisible
        else desiredEnable && !isUiVisible

    protected fun syncUi(
        desiredEnable: Boolean,
        isEnabled: Boolean
    ) {
        if (!isEnabled && desiredEnable) enable(autoDelay)
        else if (!isEnabled && !desiredEnable) disable(autoDelay)
    }


    private fun listenForInsets() {
        if (!keepLayout) {
            containerView?.apply {
                ViewCompat.setOnApplyWindowInsetsListener(
                    this, insetsListener
                )
            }
        }
    }

    private fun stopListeningForInsets() {
        if (!keepLayout) {
            containerView?.apply {
                ViewCompat.setOnApplyWindowInsetsListener(
                    this, null
                )
            }
        }
    }

    private val insetsListener: OnApplyWindowInsetsListener =
        OnApplyWindowInsetsListener { view, insets ->
            view.setPadding(
                insets.systemWindowInsetLeft, insets.systemWindowInsetTop,
                insets.systemWindowInsetRight, insets.systemWindowInsetBottom
            )
            insets.consumeSystemWindowInsets()
        }


    fun release() {
        removeEnableQueuedRunnable()
        removeDisableQueuedRunnable()
        stopListeningForInsets()
        removeListener()
    }

    fun listener(listener: SystemUiHelper.OnVisibilityChangeListener) {
        this.onVisibilityChangeListener = listener
    }

    fun removeListener() {
        onVisibilityChangeListener = null
    }


}
