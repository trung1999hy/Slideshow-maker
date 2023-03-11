package com.datnt.slideshowmaker.basefragment

import android.os.SystemClock
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.datnt.slideshowmaker.R

fun AppCompatActivity.replaceFragment(@IdRes frameId: Int, fragment: Fragment?,
                                      tag: String, addToBackStack: Boolean, enableAnimation: Boolean) {
}
private inline fun FragmentManager.transactWithAnimationT6(action: FragmentTransaction.() -> Unit, name: String) {
    beginTransaction().setCustomAnimations(R.anim.enter_from_right_t6, R.anim.exit_to_left_t6,
        R.anim.enter_from_left_t6, R.anim.exit_to_right_t6)
        .apply {
            action()
        }.addToBackStack(name).commitAllowingStateLoss()
}
fun AppCompatActivity.addFragmentWithAnimationT6(
    fromFragment: Fragment? = null, toFragment: Fragment?,
    tag: String, @IdRes frameId: Int
) {
    supportFragmentManager.transactWithAnimationT6({
        fromFragment?.let {
            hide(it)
        }
        toFragment?.let {
            add(frameId, toFragment, tag)
        }
    }, tag)
}
