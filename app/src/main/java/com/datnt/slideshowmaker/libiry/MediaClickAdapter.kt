package com.datnt.slideshowmaker.libiry

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.datnt.slideshowmaker.R
import com.datnt.slideshowmaker.Ultis.click
import com.datnt.slideshowmaker.databinding.ItemMediaBinding
import com.datnt.slideshowmaker.libiry.model.MediaItem


class MediaClickAdapter(

    private val activity: Activity,
    var itemClick: ((ArrayList<MediaItem>) -> Unit)? = null
) :
    RecyclerView.Adapter<MediaClickAdapter.MediaViewHolder>() {
    var medias: ArrayList<MediaItem> = arrayListOf()

    inner class MediaViewHolder(
        val binding: ItemMediaBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaItem: MediaItem) {
            binding.txtDuration.text = mediaItem.duration.toString()
            Glide.with(binding.root).load(mediaItem.uri).into(binding.imgPreview)
            binding.tvCount1.visibility = View.VISIBLE
            itemClick?.invoke(medias)
            binding.tvCount1.click {
                medias.remove(mediaItem)
                notifyDataSetChanged()
                itemClick?.invoke(medias)

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
            )
        )
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(medias.get(position))
    }

    fun setAdapter(medias: ArrayList<MediaItem>) {
        this.medias = medias
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = medias.size
}
