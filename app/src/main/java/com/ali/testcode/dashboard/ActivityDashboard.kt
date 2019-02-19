package com.ali.testcode.dashboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.ali.testcode.BaseActivity
import com.ali.testcode.R
import com.ali.testcode.TouristDestination
import com.ali.testcode.detailLocation.ActivityDetailLocation
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.tasks.OnFailureListener
import org.jetbrains.anko.alert
import android.view.Menu
import android.view.MenuItem
import com.ali.testcode.MapActivity


class ActivityDashboard : BaseActivity(), ContractDashboard.View {

    override fun progressBar(status: Boolean) {
        var visibility = View.GONE
        if (status) {
            visibility = View.VISIBLE
        }
        mPbDashboard.visibility = visibility
    }

    override fun setData(toursitDestination: MutableList<TouristDestination>) {
        mAdapterDashboard.dataChange(toursitDestination)
    }

    val mPresenter = PresenterDashboard(this)
    lateinit var mRcvDashboard: RecyclerView
    lateinit var mAdapterDashboard: AdapterDashboard
    lateinit var mPbDashboard: ProgressBar
    private var mGooglApiCLient: GoogleApiClient? = null
    lateinit var mLocationManager:LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        setToolbar(R.id.toolbar)
        initRcv()
        mPresenter.getDataLocation()
        statusCheck()
//        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        getLastLocation()
    }

    private fun setToolbar(toolbarId:Int){
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(toolbarId)
        setSupportActionBar(toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.title = title
    }

    private fun bindView() {
        mRcvDashboard = findViewById(R.id.rcv_dashboard)
        mPbDashboard = findViewById(R.id.pb_dashboard)
        mGooglApiCLient = googleSignInClient()
    }

    private fun initRcv() {
        val list: MutableList<TouristDestination> = ArrayList()
        val gridLayoutManager = GridLayoutManager(this, 2)
        mRcvDashboard.setHasFixedSize(true)
        mRcvDashboard.layoutManager = gridLayoutManager
        val listener = object : AdapterDashboard.Listener {
            override fun clickListener(location: TouristDestination) {
                intentToDetailLocation(location)
            }
        }
        mAdapterDashboard = AdapterDashboard(list, this, listener)
        mRcvDashboard.adapter = mAdapterDashboard
    }

    private fun intentToDetailLocation(location: TouristDestination) {
        val intent = Intent(this, ActivityDetailLocation::class.java)
        intent.putExtra("TouristDestination", location)
        startActivity(intent)
    }

    override fun onBackPressed() {
        alert(getString(R.string.alert_sign_out), getString(R.string.sign_out)) {
            positiveButton(R.string.ok) {
                //                Auth.GoogleSignInApi.signOut(googleSignInClient())
                signOut(this@ActivityDashboard, mGooglApiCLient!!)
            }
            negativeButton(getString(R.string.cancel)) {

            }
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dashboard_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.myLocation -> {
                getLastLocation()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun statusCheck() {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }

    private fun buildAlertMessageNoGps() {
        alert("Your GPS seems to be disabled, do you want to enable it?", "Location"
        ) {
            positiveButton("Yes") {
                //                Auth.GoogleSignInApi.signOut(googleSignInClient())
                startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }
            negativeButton("No") {

            }
        }.show()

    }

    fun getLastLocation() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        // Get last known recent mLocation using new Google Play Services SDK (v11+)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            val locationClient = getFusedLocationProviderClient(this)
            locationClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            val intent = Intent(this@ActivityDashboard, MapActivity::class.java)
                            intent.putExtra("lat", location.latitude)
                            intent.putExtra("lng", location.longitude)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener(OnFailureListener { e ->
                        Log.d("MapDemoActivity", "Error trying to get last GPS mLocation")
                        e.printStackTrace()
                    })
        } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        101)
        }
    }

    fun onLocationChanged(location: Location) {
        // New mLocation has now been determined
        val msg = "Updated TouristDestination: " +
                java.lang.Double.toString(location.getLatitude()) + "," +
                java.lang.Double.toString(location.getLongitude())
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        // You can now create a LatLng Object for use with maps
//        val latLng = LatLng(mLocation.getLatitude(), mLocation.getLongitude())
    }
}
