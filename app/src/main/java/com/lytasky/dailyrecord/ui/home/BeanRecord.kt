package com.lytasky.dailyrecord.ui.home

import com.lytasky.dailyrecord.base.BaseBean

/**
 *  Created by Liaoxinhui on 2021/3/13 8:05 PM
 */
class BeanRecord : BaseBean() {
    lateinit var dayNumber:String
    lateinit var weekNumber:String
    lateinit var content:String
    lateinit var recordPic:MutableList<String>
    lateinit var time:String
}