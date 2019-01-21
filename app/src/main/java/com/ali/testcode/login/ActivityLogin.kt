package com.ali.testcode.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ali.testcode.R
import com.ali.testcode.dashboard.ActivityDashboard
import com.google.android.gms.common.SignInButton

class ActivityLogin : AppCompatActivity() {

    private lateinit var mBtnGoogle:SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindView()

        mBtnGoogle.setOnClickListener {
            intentToDashboard()
        }
    }

    private fun bindView(){
        mBtnGoogle = findViewById(R.id.google_sign_in_button)
    }

    private fun intentToDashboard(){
        val intent = Intent(this, ActivityDashboard::class.java)
        startActivity(intent)
        finish()
    }
}
