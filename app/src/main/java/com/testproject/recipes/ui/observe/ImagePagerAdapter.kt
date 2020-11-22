package com.testproject.recipes.ui.observe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import com.testproject.recipes.R

class ImagePagerAdapter(private var imageURls: List<String>, private val context: Context) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)
    override fun getCount(): Int = imageURls.size
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rootView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.image_page, container, false)
        Picasso.get()
            .load(imageURls[position])
            .placeholder(R.drawable.red_box)
            .error(R.drawable.red_box)
            .into(rootView.findViewById<ImageView>(R.id.page_imageView))
        container.addView(rootView)
        return rootView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}