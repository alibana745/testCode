package com.ali.testcode.login

import android.content.Intent
import android.os.Bundle
import com.ali.testcode.BaseActivity
import com.ali.testcode.R
import com.ali.testcode.dashboard.ActivityDashboard
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient

class ActivityLogin : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {

    private lateinit var mBtnGoogle:SignInButton
    private val RC_SIGN_IN = 107

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindView()

        mBtnGoogle.setOnClickListener {
            signIn(googleSignInClient())
        }
    }

    private fun signIn(googleSignClient: GoogleApiClient?) {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleSignClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            val account = result.signInAccount
            if (account != null) {
                intentToDashboard()
//                val tokenAccount = account.idToken
//                val emailAccount = account.email
//                val idAccount = account.id
//                val imageAccount = account.photoUrl.toString()
//                val displayNameAccount = account.displayName
//                val familyNameAccount = account.familyName
//                val givenNameAccount = account.givenName
            }
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
