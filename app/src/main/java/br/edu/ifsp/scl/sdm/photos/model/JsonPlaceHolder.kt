package br.edu.ifsp.scl.sdm.photos.model

import android.content.Context

class JsonPlaceHolder(context: Context) {
    companion object{
        const val PHOTOS_ENDPOINT = "https://jsonplaceholder.typicode.com/photos/"
        @Volatile
        private var INSTANCE: JsonPlaceHolder? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this){
            INSTANCE ?: JsonPlaceHolder(context).also {
                INSTANCE = it
            }
        }
    }
}