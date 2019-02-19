package com.ali.testcode.detailLocation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.ali.testcode.BaseActivity
import com.ali.testcode.TouristDestination
import com.ali.testcode.R
import com.bumptech.glide.Glide

class ActivityDetailLocation : BaseActivity() {

    private lateinit var mTvTitle:TextView
    private lateinit var mTvDetail:TextView
    private lateinit var mTvAddress:TextView
    private lateinit var mImgDetail:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_location)
        bindView()
        initData()
    }

    private fun bindView(){
        mTvTitle = findViewById(R.id.title)
        mTvDetail = findViewById(R.id.detail)
        mTvAddress = findViewById(R.id.address)
        mImgDetail = findViewById(R.id.image)
    }

    private fun initData(){
        val location = intent.extras.getSerializable("TouristDestination")as TouristDestination
        setToolbar(R.id.toolbar, location.title)
        mTvTitle.text = location.title
        mTvAddress.text = location.address
        mTvDetail.text = location.detail
        Glide.with(this).load(location.urlImage).into(mImgDetail)
    }
}
