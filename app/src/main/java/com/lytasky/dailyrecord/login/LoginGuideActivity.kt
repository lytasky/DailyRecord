package com.lytasky.dailyrecord.login

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lytasky.dailyrecord.R
import org.w3c.dom.Text

class LoginGuideActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_guide)
        initView();
    }

    private fun initView() {
        findViewById<ImageView>(R.id.back_action).setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.wx_login).setOnClickListener {
            Toast.makeText(this, "微信登陆", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.sina_login).setOnClickListener {
            Toast.makeText(this, "新浪微博登陆", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.qq_login).setOnClickListener {
            Toast.makeText(this, "QQ登陆", Toast.LENGTH_SHORT).show()
        }
    }
}