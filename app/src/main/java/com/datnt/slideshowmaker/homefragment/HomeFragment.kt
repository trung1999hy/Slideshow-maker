package com.datnt.slideshowmaker.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.datnt.slideshowmaker.Ultis.click
import com.datnt.slideshowmaker.basefragment.BaseFragment
import com.datnt.slideshowmaker.databinding.HomescreenfragmentBinding
import com.datnt.slideshowmaker.libiry.LibiryFragment

@Suppress("DEPRECATION")
class HomeFragment : BaseFragment() {
    private lateinit var binding: HomescreenfragmentBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeFragmentModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomescreenfragmentBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeFragmentModel::class.java]
        binding.lnCreate.click {
            openMyProjectFragment()
        }


    }

    override fun initActions() {
    }

    private fun openMyProjectFragment() {

        pushScreenAsNormalWithT6(LibiryFragment.newInstance(), LibiryFragment::class.java.name)
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun getContentResId(): Int {
        TODO("Not yet implemented")
    }

}