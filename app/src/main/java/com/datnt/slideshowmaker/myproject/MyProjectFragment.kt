package com.datnt.slideshowmaker.myproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.datnt.slideshowmaker.basefragment.BaseFragment
import com.datnt.slideshowmaker.databinding.MyprojectfragmentBinding

class MyProjectFragment : BaseFragment() {
    private lateinit var binding: MyprojectfragmentBinding
    companion object{
        fun newInstance() = MyProjectFragment()
    }
    private lateinit var viewModel: MyProjectModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyprojectfragmentBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MyProjectModel::class.java]
       initActions()
    }

    override fun initActions() {
        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun getContentResId(): Int {
        TODO("Not yet implemented")
    }
}