package com.lytasky.dailyrecord.login

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lytasky.dailyrecord.R
import org.w3c.dom.Text

class LoginGuideActivity : AppCompatActivity() {

    private lateinit var userMailET: EditText;
    private lateinit var userPwdET: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_guide)
        initView();
    }

    private fun initView() {
        findViewById<ImageView>(R.id.back_action).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.login_btn).setOnClickListener {
            Toast.makeText(
                this,
                "登陆" + userMailET.editableText + "====>" + userPwdET.editableText,
                Toast.LENGTH_SHORT
            ).show()
        }
        userMailET = findViewById(R.id.user_mail)
        userPwdET = findViewById(R.id.user_pwd)
    }
}