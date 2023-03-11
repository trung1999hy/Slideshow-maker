package com.datnt.slideshowmaker.libiry

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datnt.slideshowmaker.libiry.model.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LibiryViewModel() : ViewModel() {
    private val _medias = MutableLiveData<ArrayList<MediaItem>>()
    val medias: LiveData<ArrayList<MediaItem>>
        get() = _medias

    private val _videos = MutableLiveData<ArrayList<MediaItem>>()
    val videos: LiveData<ArrayList<MediaItem>>
        get() = _videos

    private val _images = MutableLiveData<ArrayList<MediaItem>>()
    val images: LiveData<ArrayList<MediaItem>>
        get() = _images

    fun getAllVideos(contentResolver: ContentResolver) {
        viewModelScope.launch {
            _videos.value = getAllVideo(contentResolver)
        }
    }

    private suspend fun getAllVideo(contentResolver: ContentResolver): ArrayList<MediaItem> =
        withContext(Dispatchers.IO) {
            val video = arrayListOf<MediaItem>()
            val collectionVideo: Uri =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Video.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL
                    )
                } else {
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }

            val projectionVideo = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_ADDED
            )

            val sortOrderVideo = "${MediaStore.Video.Media.DATE_ADDED} DESC"

            contentResolver.query(
                collectionVideo,
                projectionVideo,
                null,
                null,
                sortOrderVideo
            )?.use { cursor ->
                try {
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                    val nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                    val durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                    val dateAddColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)

                    val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val name = cursor.getString(nameColumn)
                        val duration = cursor.getInt(durationColumn)
                        val size = cursor.getInt(sizeColumn)
                        val dateAdd = cursor.getLong(dateAddColumn)
                        val date = getDate(dateAdd * 1000, "dd/MM/yyyy").toString()

                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        // Stores column values and the contentUri in a local object
                        // that represents the media file.
                        video.add(
                            MediaItem(
                                id,
                                true,
                                contentUri,
                                name,
                                duration,
                                size,
                                date
                            )
                        )
                        println("ABCD: $duration")
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }

            }
            return@withContext video
        }

    fun getAllImages(contentResolver: ContentResolver) {
        viewModelScope.launch {
            _images.value = getAllImage(contentResolver)
        }
    }

    private suspend fun getAllImage(contentResolver: ContentResolver): ArrayList<MediaItem> =
        withContext(Dispatchers.IO) {
            val images = arrayListOf<MediaItem>()
            val collectionImage: Uri =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL
                    )
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }
            val projectionImage = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED
            )
            val sortOrderImage = "${MediaStore.Images.Media.DATE_ADDED} DESC"


            viewModelScope.launch(Dispatchers.Main) {
                contentResolver.query(
                    collectionImage,
                    projectionImage,
                    null,
                    null,
                    sortOrderImage
                )?.use { cursor ->
                    try {
                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                        val nameColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                        val dateAddColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

                        val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                        while (cursor.moveToNext()) {
                            val id = cursor.getLong(idColumn)
                            val name = cursor.getString(nameColumn)

                            val size = cursor.getInt(sizeColumn)
                            val dateAdd = cursor.getLong(dateAddColumn)
                            val date = getDate(dateAdd * 1000, "dd/MM/yyyy").toString()

                            val contentUri: Uri = ContentUris.withAppendedId(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                id
                            )
                            // Stores column values and the contentUri in a local object
                            // that represents the media file.
                            images.add(MediaItem(id, false, contentUri, name, null, size, date))
                        }
                    } catch (e: Exception) {
                        println("Error Image: ${e.message}")
                    }
                }
            }
            return@withContext images
        }


    fun getAllMediaFile(contentResolver: ContentResolver) {
        viewModelScope.launch {
            val list = arrayListOf<MediaItem>()
            list.addAll(getAllVideo(contentResolver))
            list.addAll(getAllImage(contentResolver))
            list.sortWith(CustomComparator())

            val groupedMapMap: Map<String, List<MediaItem>> = list.groupBy {
                it.dateAdd!!
            }


            groupedMapMap.entries.forEach {
                list.addAll(it.value)
            }

            _medias.value = list
        }
    }

    class CustomComparator : Comparator<MediaItem> {
        override fun compare(p0: MediaItem?, p1: MediaItem?): Int {
            val data1 = p0?.dateAdd ?: ""
            val data2 = p1?.dateAdd ?: ""

            if (data1 == "") {
                return -1
            }
            if (data2 == "") {
                return -1
            }
            val format = SimpleDateFormat("dd/MM/yyyy")
            val date1 = format.parse(data1);
            val date2 = format.parse(data2);
            return date2.compareTo(date1)
        }

    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

}
