package com.datnt.slideshowmaker.libiry

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.datnt.slideshowmaker.R
import com.datnt.slideshowmaker.Ultis.click
import com.datnt.slideshowmaker.databinding.ItemMediaBinding
import com.datnt.slideshowmaker.libiry.model.MediaItem


class MediaAdapter(

    private val activity: Activity,
    var itemClick: ((ArrayList<MediaItem>) -> Unit)? = null
) :
    RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {
    private var count: Int = 0
    var medias: ArrayList<MediaItem> = arrayListOf()
    var listcount: ArrayList<MediaItem> = arrayListOf()

    inner class MediaViewHolder(
        val binding: ItemMediaBinding,
        var itemClick: ((ArrayList<MediaItem>) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaItem: MediaItem) {
            binding.txtDuration.text = mediaItem.duration.toString()
            Glide.with(binding.root).load(mediaItem.uri).into(binding.imgPreview)
            val position = listcount.indexOf(mediaItem)
            binding.tvCount.text = (position+1).toString()
            binding.tvCount.visibility = if (position == -1) View.GONE else View.VISIBLE
            binding.root.click {
                checklistItemcount(mediaItem)
                itemClick?.invoke(listcount)
            }


        }

        private fun checklistItemcount(item: MediaItem) {
            var searchitemcount =
                listcount.filter {
                    it == item
                }.size
            if (searchitemcount == 0) {
                listcount.add(item)
                binding.tvCount.text = listcount.size.toString()
                binding.tvCount.visibility = View.VISIBLE
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_media,
                parent,
                false
            ), itemClick
        )
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(medias.get(position))

    }

    fun setAdapter(medias: ArrayList<MediaItem>) {
        this.medias = medias
        notifyDataSetChanged()
    }

    fun upDateListMediaClick(item: ArrayList<MediaItem>) {
        listcount = item
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = medias.size
}
