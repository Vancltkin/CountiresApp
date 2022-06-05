package com.example.countriesapp

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.*

fun formatNumber(number: Long): String {
    return NumberFormat.getInstance().format(number)
}

suspend fun loadSvg(imageView: ImageView, url: String) {
    if (url.lowercase(Locale.ENGLISH).endsWith("svg")) {
        val imageLoader = ImageLoader.Builder(imageView.context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
        val request = ImageRequest.Builder(imageView.context)
            .data(url)
            .target(imageView)
            .build()
        coroutineScope {
            withContext(Dispatchers.IO) {
                imageLoader.execute(request)
            }
        }
    } else {
        imageView.load(url)
    }
}