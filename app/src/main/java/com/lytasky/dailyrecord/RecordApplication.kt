package com.lytasky.dailyrecord

import android.app.Application
import com.lytasky.dailyrecord.base.database.DatabaseHelper
import com.lytasky.dailyrecord.utils.StaticsUtils

class RecordApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        StaticsUtils.application = this;
        DatabaseHelper.initRecordDatabase(this);
    }

}