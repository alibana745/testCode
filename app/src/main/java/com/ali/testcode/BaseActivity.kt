package com.ali.testcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.ali.testcode.login.ActivityLogin
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

abstract class BaseActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener{

    fun googleSignInClient(): GoogleApiClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("725778244408-8laclhsm1bek808cvgkp4o6e82qaie94.apps.googleusercontent.com")
                .requestEmail()
                .build()

        return GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun signOut(activity: Activity, googleApiClient: GoogleApiClient){
        Auth.GoogleSignInApi.signOut(googleApiClient)
        val intent = Intent(activity.applicationContext,
                ActivityLogin::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    fun setToolbar(toolbarId: Int, title: String) {
        val toolbar = findViewById<Toolbar>(toolbarId)
        setSupportActionBar(toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.title = title
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}