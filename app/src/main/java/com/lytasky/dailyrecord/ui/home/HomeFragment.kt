package com.lytasky.dailyrecord.ui.home

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lytasky.dailyrecord.R
import com.lytasky.dailyrecord.base.database.DatabaseHelper
import com.lytasky.dailyrecord.utils.StringUtils
import com.lytasky.dailyrecord.utils.TimeUtils

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recordRecyclerView: RecyclerView
    private lateinit var recordSwipeRefresh: SwipeRefreshLayout
    private lateinit var recordRecyclerAdapter: RecordRecyclerAdapter;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initSwipeRefreshLayout(root);
        initRecyclerView(root);
        return root
    }

    private fun initSwipeRefreshLayout(root: View) {
        recordSwipeRefresh = root.findViewById(R.id.record_swipe_refresh);
        recordSwipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable() {
                recordSwipeRefresh.isRefreshing = false
            }, 1000); // 延时1秒
        }
    }

    private fun initRecyclerView(root: View) {
        recordRecyclerView = root.findViewById(R.id.record_recyclerview)
        recordRecyclerAdapter = RecordRecyclerAdapter()
        recordRecyclerView.layoutManager = LinearLayoutManager(activity)
        recordRecyclerView.adapter = recordRecyclerAdapter
    }

    private fun requestRecord() {
        var recordList: MutableList<BeanRecord> = mutableListOf()
        var result = DatabaseHelper.getAllRecord();
        for (record in result) {
            var beanRecord = BeanRecord();
            //获取北京时区的时间
            var createTimeBJZ = TimeUtils.getMillisPlus8H(record.createdTime);
            beanRecord.dayNumber =
                TimeUtils.getTimeStringByMillis(createTimeBJZ, TimeUtils.getSafeDateFormat("dd"))
            beanRecord.weekNumber = TimeUtils.getTimeStringByMillis(
                createTimeBJZ,
                TimeUtils.getSafeDateFormat("MM月")
            ) + "/" + TimeUtils.getChineseWeek(TimeUtils.millis2Date(createTimeBJZ))
            beanRecord.time =
                TimeUtils.getTimeStringByMillis(createTimeBJZ, TimeUtils.getSafeDateFormat("HH:mm"))
            beanRecord.content = record.content;
            beanRecord.recordPic = mutableListOf();
            if (TextUtils.isEmpty(record.picArray)) {
                beanRecord.recordPic = mutableListOf();
            } else {
                beanRecord.recordPic = StringUtils.segStringToArray(record.picArray, ",")
            }
            recordList.add(beanRecord);
        }
        recordRecyclerAdapter.setRecordList(recordList);
        recordRecyclerAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        requestRecord();
    }
}