package com.task.yogahaz.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


object Utils {


    // loading the image with glide and applying center crop and rounded corners also managing placeholders
    fun loadImage(context: Context
                  , path : String
                  , imageView: ImageView
                  ,placeholder: Int
                  ,cornerRadiusValue: Int
    ) {
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusValue))

        Glide.with(context)
            .load(path)
            .apply(requestOptions)
            .placeholder(placeholder)
            .into(imageView)

    }
}
