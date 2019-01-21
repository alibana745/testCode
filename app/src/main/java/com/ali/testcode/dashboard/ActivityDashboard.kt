package com.ali.testcode.dashboard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.ali.testcode.Location
import com.ali.testcode.R
import com.ali.testcode.detailLocation.ActivityDetailLocation

class ActivityDashboard : AppCompatActivity(), ContractDashboard.View {
    override fun setData(Locations: MutableList<Location>) {
        mAdapterDashboard.dataChange(Locations)
    }

    val mPresenter = PresenterDashboard(this)
    lateinit var mRcvDashboard:RecyclerView
    lateinit var mAdapterDashboard: AdapterDashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        initRcv()
        mPresenter.getDataLocation()
    }

    private fun bindView(){
        mRcvDashboard = findViewById(R.id.rcv_dashboard)
    }

    private fun initRcv(){
        val list:MutableList<Location> = ArrayList()
        val gridLayoutManager = GridLayoutManager(this, 2)
        mRcvDashboard.setHasFixedSize(true)
        mRcvDashboard.layoutManager = gridLayoutManager
        val listener = object :AdapterDashboard.Listener{
            override fun clickListener(location: Location) {
                intentToDetailLocation(location)
            }
        }
        mAdapterDashboard = AdapterDashboard(list, this, listener)
        mRcvDashboard.adapter = mAdapterDashboard
    }

  private fun intentToDetailLocation(location:Location){
      val intent = Intent(this, ActivityDetailLocation::class.java)
      intent.putExtra("Location", location)
      startActivity(intent)
  }
}
