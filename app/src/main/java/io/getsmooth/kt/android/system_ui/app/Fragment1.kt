package io.getsmooth.kt.android.system_ui.app


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import io.getsmooth.kt.android.system_ui.*
import io.getsmooth.kt.android.system_ui_lifecycle.fullScreenAuto
import io.getsmooth.kt.android.system_ui_lifecycle.immersiveAuto
import io.getsmooth.kt.android.system_ui_lifecycle.normalAuto
import kotlinx.android.synthetic.main.fragment_fragment1.*


/**
 * A simple [Fragment] subclass.
 *
 */
class Fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullScreenAuto{
        }
    }


}
