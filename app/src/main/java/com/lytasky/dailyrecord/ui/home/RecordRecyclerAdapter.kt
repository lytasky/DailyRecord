package com.lytasky.dailyrecord.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lytasky.dailyrecord.R
import com.lytasky.dailyrecord.gallery.GalleryActivity
import com.lytasky.dailyrecord.utils.StringUtils
import com.lytasky.dailyrecord.utils.ViewUtils


/**
 *  Created by Liaoxinhui on 2021/3/13 8:04 PM
 */
class RecordRecyclerAdapter(activity: Activity) :
    RecyclerView.Adapter<RecordRecyclerAdapter.ViewHolder>() {

    private var recordList: MutableList<BeanRecord> = mutableListOf();
    private var context: Context = activity;

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_record, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = recordList[position];
        viewHolder.dayNumber.text = item.dayNumber
        viewHolder.weekNumber.text = item.weekNumber
        viewHolder.content.text = item.content
        viewHolder.recordPicLl.removeAllViews();
        if (item.recordPic.isNotEmpty()) {
            for (pic in item.recordPic) {
                addImageView(viewHolder, pic, item);
            }
        }
        viewHolder.time.text = item.time
    }

    private fun addImageView(viewHolder: ViewHolder, path: String, item: BeanRecord) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        var picIv = ViewUtils.addPicImageView(viewHolder.recordPicLl, context);
        picIv.setOnClickListener {
            var intent = Intent(context, GalleryActivity::class.java)
            intent.putExtra("banner_list", StringUtils.arrayToSegString(item.recordPic, ","))
            intent.putExtra("banner_index", item.recordPic.indexOf(path))
            context.startActivity(intent)
        }
        Glide.with(viewHolder.itemView.context)
            .load(path)
            .into(picIv);
    }

    override fun getItemCount() = recordList.size

    fun setRecordList(recordList: MutableList<BeanRecord>) {
        this.recordList = recordList;
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayNumber: TextView = view.findViewById(R.id.day_number)
        val weekNumber: TextView = view.findViewById(R.id.week_number)
        val content: TextView = view.findViewById(R.id.content)
        val recordPicLl: LinearLayout = view.findViewById(R.id.record_pic_ll)
        val time: TextView = view.findViewById(R.id.time)
    }
}