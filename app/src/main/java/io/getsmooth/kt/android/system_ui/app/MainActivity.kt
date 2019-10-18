package io.getsmooth.kt.android.system_ui.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import io.getsmooth.kt.android.system_ui.FullScreen
import io.getsmooth.kt.android.system_ui.lowProfile

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.container)
        ) { view, insets ->
            println("stable Insets : ${insets.stableInsetTop}")
            println("system window Insets : ${insets.systemWindowInsetTop}")
            println("has insets : ${insets.hasInsets()}")
            println("has stable insets : ${insets.hasStableInsets()}")
            println("has system window insets : ${insets.hasSystemWindowInsets()}")
            println("----------------")

            view.setPadding(
                insets.systemWindowInsetLeft, insets.systemWindowInsetTop,
                insets.systemWindowInsetRight, insets.systemWindowInsetBottom
            )

//            ViewCompat.onApplyWindowInsets(findViewById(R.id.container), insets)
//            window.decorView.onApplyWindowInsets(insets)
            insets.consumeSystemWindowInsets()
        }

        println("container : ${findViewById<ViewGroup>(R.id.container)}")
        println(
            "container 2: ${
            (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            }"
        )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, Fragment1())
                .commit()

            Handler().postDelayed(Runnable {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, Fragment2())
                    .commit()
            }, 5000)


            Handler().postDelayed(Runnable {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, Fragment3())
                    .commit()
            }, 10000)

            val f = Fragment1()
            Handler().postDelayed(Runnable {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, f)
                    .commit()
            }, 15000)


            Handler().postDelayed(Runnable {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, Fragment4())
                    .commit()
            }, 20000)


        }

    }

    fun View.setMarginTop(marginTop: Int) {
        val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        menuLayoutParams.setMargins(0, marginTop, 0, 0)
        this.layoutParams = menuLayoutParams
    }

}
