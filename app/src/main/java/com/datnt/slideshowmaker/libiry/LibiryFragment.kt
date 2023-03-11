package com.datnt.slideshowmaker.libiry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.datnt.slideshowmaker.basefragment.BaseFragment
import com.datnt.slideshowmaker.databinding.FragmentLibiryBinding
import com.datnt.slideshowmaker.libiry.model.MediaItem

class LibiryFragment : BaseFragment() {

    companion object {
        fun newInstance() = LibiryFragment()
    }

    private lateinit var binding: FragmentLibiryBinding
    private lateinit var viewModel: LibiryViewModel
    private lateinit var mediaClickAdapter: MediaClickAdapter
    private lateinit var mediaAdapter: MediaAdapter
    private val medias: ArrayList<MediaItem> = arrayListOf()
    override fun initActions() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun getContentResId(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibiryBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[LibiryViewModel::class.java]
        viewModel.getAllMediaFile(requireActivity().contentResolver)
         mediaClickAdapter = MediaClickAdapter(activity = requireActivity()){
             mediaAdapter.upDateListMediaClick(it)
             if (it.size == 0) binding.rcvMediaClick.visibility = View.GONE

        }

         mediaAdapter = MediaAdapter(activity = requireActivity()) {

            binding.rcvMediaClick.visibility = View.VISIBLE
            mediaClickAdapter.setAdapter(it)

        }
        binding.rcvMediaClick.adapter = mediaClickAdapter
        binding.rcvMediaClick.layoutManager = LinearLayoutManager(requireContext(),  LinearLayoutManager.HORIZONTAL, false)
        binding.rcvMediaContainer.adapter = mediaAdapter
        viewModel.medias.observe(viewLifecycleOwner) {
            mediaAdapter.setAdapter(it)
        }
        binding.rcvMediaContainer.layoutManager =
            GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
    }


}