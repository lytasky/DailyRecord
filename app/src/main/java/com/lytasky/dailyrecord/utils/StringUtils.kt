package com.lytasky.dailyrecord.utils

import android.text.TextUtils

object StringUtils {

    fun arrayToSegString(list: List<String>, seg: String): String {
        var result = "";
        for (item in list) {
            result = result.plus(item).plus(seg);
        }
        return if (TextUtils.isEmpty(result)) {
            result;
        } else {
            result.substring(0, result.length - 1);
        }
    }

    fun segStringToArray(target: String, seg: String): MutableList<String> {
        if (TextUtils.isEmpty(target)) {
            return mutableListOf();
        }
        return target.split(seg).toMutableList();
    }
}