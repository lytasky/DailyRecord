package com.lytasky.dailyrecord.gallery

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lytasky.dailyrecord.R
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class GalleryAdapter : BaseBannerAdapter<BeanGallery>() {

    override fun bindData(
        holder: BaseViewHolder<BeanGallery>,
        data: BeanGallery?,
        position: Int,
        pageSize: Int
    ) {
        var bannerImage: ImageView = holder.itemView.findViewById(R.id.banner_image);
        Glide.with(holder.itemView.context).load(data!!.imageUrl).into(bannerImage);
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_gallery;
    }


}