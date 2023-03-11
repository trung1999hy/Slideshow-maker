package com.datnt.slideshowmaker.basefragment

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.datnt.slideshowmaker.R
import com.datnt.slideshowmaker.homefragment.HomeFragment

abstract class BaseFragment : Fragment(){
    abstract fun initActions()
    abstract fun initData()
    abstract fun getContentResId(): Int

    fun pushScreenAsNormalWithT6(
        fragment: Fragment,
        tag: String,
        @IdRes frameId: Int = R.id.home_screen_fragment,
        fromFragment: Fragment? = null
    ) {
        (activity as? AppCompatActivity)?.addFragmentWithAnimationT6(
            fromFragment ?: this,
            fragment,
            tag,
            frameId
        )
    }





}