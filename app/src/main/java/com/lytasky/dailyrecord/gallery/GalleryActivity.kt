package com.lytasky.dailyrecord.gallery

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lytasky.dailyrecord.R
import com.lytasky.dailyrecord.utils.StringUtils
import com.zhpan.bannerview.BannerViewPager


class GalleryActivity : AppCompatActivity() {

    private lateinit var mViewPager: BannerViewPager<BeanGallery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initView();
    }

    private fun initView() {
        var bannerStr: String = intent.getStringExtra("banner_list");
        var bannerIndex: Int = intent.getIntExtra("banner_index", 0);
        var bannerList: MutableList<BeanGallery> = mutableListOf();
        for (item in StringUtils.segStringToArray(bannerStr, ",")) {
            var beanGallery = BeanGallery();
            beanGallery.imageUrl = item;
            bannerList.add(beanGallery)
        }
        mViewPager = findViewById(R.id.banner_view)
        mViewPager.setLifecycleRegistry(lifecycle)
            .setAdapter(GalleryAdapter())
            .create(bannerList)
        mViewPager.currentItem = bannerIndex;
        mViewPager.setCanLoop(false);
        var closeAction: ImageView = findViewById(R.id.close_action);
        closeAction.setOnClickListener { finish() }
    }
}