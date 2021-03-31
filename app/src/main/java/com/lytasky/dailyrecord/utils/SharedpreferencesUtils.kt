package com.lytasky.dailyrecord.utils

import android.content.Context
import android.content.SharedPreferences

object SharePreferencesUtils {

    private lateinit var sSharePreferences:SharedPreferences;
    private const val CONFIG_SP_NAME = "daily_record_config_sp"

    private fun getSharePreferences():SharedPreferences {
        if (sSharePreferences == null) {
            sSharePreferences = StaticsUtils.application.getSharedPreferences(CONFIG_SP_NAME,Context.MODE_PRIVATE)
        }
        return  sSharePreferences;
    }

    fun setParam(
        key: String?,
        `object`: Any
    ) {
        val type = `object`.javaClass.simpleName
        val sp = getSharePreferences();
        val editor = sp.edit()
        when (type) {
            "String" -> {
                editor.putString(key, `object` as String)
            }
            "Integer" -> {
                editor.putInt(key, (`object` as Int))
            }
            "Boolean" -> {
                editor.putBoolean(key, (`object` as Boolean))
            }
            "Float" -> {
                editor.putFloat(key, (`object` as Float))
            }
            "Long" -> {
                editor.putLong(key, (`object` as Long))
            }
        }
        editor.commit()
    }

    fun getParam(
        key: String?,
        defaultObject: Any
    ): Any? {
        val type = defaultObject.javaClass.simpleName
        val sp = getSharePreferences();
        when (type) {
            "String" -> {
                return sp.getString(key, defaultObject as String)
            }
            "Integer" -> {
                return sp.getInt(key, (defaultObject as Int))
            }
            "Boolean" -> {
                return sp.getBoolean(key, (defaultObject as Boolean))
            }
            "Float" -> {
                return sp.getFloat(key, (defaultObject as Float))
            }
            "Long" -> {
                return sp.getLong(key, (defaultObject as Long))
            }
            else -> return null
        }
    }

    fun clearAll() {
        val editor: SharedPreferences.Editor = getSharePreferences().edit()
        editor.clear().commit()
    }
}