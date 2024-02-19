package br.edu.ifsp.scl.sdm.photos.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import br.edu.ifsp.scl.sdm.photos.R
import br.edu.ifsp.scl.sdm.photos.adapter.PhotoAdapter
import br.edu.ifsp.scl.sdm.photos.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.photos.model.JsonPlaceHolder
import br.edu.ifsp.scl.sdm.photos.model.Photo
import com.android.volley.toolbox.ImageRequest

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val photoList:MutableList<Photo> = mutableListOf()
    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this,photoList)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.mainTb.apply {
            title = getString(R.string.app_name)
        })

        amb.photosSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    retrieveImage(photoList[position].url, amb.imageUrlIv)
                    retrieveImage(photoList[position].thumbnailUrl, amb.imageThumbnailIv)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //NSA
                }
            }
        }

        retrievePhotosList()
    }

    private fun retrievePhotosList() =
        JsonPlaceHolder.PhotoListRequest({ photoList ->
            photoList.also {
                photoAdapter.addAll(it)
            }
        }, {
            Toast.makeText(
                this,
                getString(R.string.request_problem),
                Toast.LENGTH_SHORT
            ).show()
        }).also {
            JsonPlaceHolder.getInstance(this).addToRequestQueue(it)
        }
    private fun retrieveImage(imageUrl: String, view: ImageView) {
        ImageRequest(
            imageUrl,
            { response ->
                view.setImageBitmap(response)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                Toast.makeText(
                    this,
                    getString(R.string.request_problem),
                    Toast.LENGTH_SHORT
                ).show()
            }).also {
            JsonPlaceHolder.getInstance(this).addToRequestQueue(it)
        }
    }
}