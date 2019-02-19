package com.ali.testcode

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mGoogleMap: GoogleMap
    private var lat = 0.0
    private var lng = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val bundle = intent.extras
        lat = bundle.getDouble("lat")
        lng = bundle.getDouble("lng")
        fun supportFragment() = supportFragmentManager.findFragmentById(R.id.mapView) as
                SupportMapFragment
        supportFragment().getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mGoogleMap = p0!!
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 10F))
        mGoogleMap.addMarker(MarkerOptions().position(LatLng(lat,
                lng)).title("Current Location"))
//        mGoogleMap.setOnCameraIdleListener(mOnCameraIdleListener)
    }
}
