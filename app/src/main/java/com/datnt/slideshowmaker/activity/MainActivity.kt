package com.datnt.slideshowmaker.activity

import android.Manifest
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.datnt.slideshowmaker.databinding.ActivityMainBinding
import com.datnt.slideshowmaker.homefragment.HomeFragment
import com.permissionx.guolindev.PermissionX

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val PERMISSION_REQUEST_CODE = 88888
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        PermissionX.init(this)
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,READ_MEDIA_IMAGES)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            android.R.id.content,
                            HomeFragment.newInstance(),
                            HomeFragment::class.java.name
                        )
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            android.R.id.content,
                            HomeFragment.newInstance(),
                            HomeFragment::class.java.name
                        )
                        .commit()
                }
            }


    }
}