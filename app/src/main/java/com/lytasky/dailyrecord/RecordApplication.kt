package com.lytasky.dailyrecord

import android.app.Application
import com.lytasky.dailyrecord.base.database.DatabaseHelper

class RecordApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseHelper.initRecordDatabase(this);
    }

}