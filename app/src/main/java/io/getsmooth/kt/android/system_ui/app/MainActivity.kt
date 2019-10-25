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


        if (savedInstanceState == null) {
            val f1 = Fragment1()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, f1)
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

}
