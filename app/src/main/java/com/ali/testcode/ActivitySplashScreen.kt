package com.ali.testcode

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ali.testcode.dashboard.ActivityDashboard
import com.ali.testcode.login.ActivityLogin
import com.google.android.gms.auth.api.signin.GoogleSignIn

class ActivitySplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val account = GoogleSignIn.getLastSignedInAccount(this)
            if (account != null){
                intentToDashboard()
            } else {
                intentToLogin()
            }
        },2000)
    }

    private fun intentToLogin(){
        val intent = Intent(this, ActivityLogin::class.java)
        startActivity(intent)
        finish()
    }

    private fun intentToDashboard(){
        val intent = Intent(this, ActivityDashboard::class.java)
        startActivity(intent)
        finish()
    }
}
