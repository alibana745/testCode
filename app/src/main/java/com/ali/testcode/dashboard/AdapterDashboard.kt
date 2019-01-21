package com.ali.testcode.dashboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ali.testcode.Location
import com.ali.testcode.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_dashboard.view.*

class AdapterDashboard (val mLocations: MutableList<Location>,
                        val mContext :Context,
                        val mListener:Listener)
    : RecyclerView.Adapter<AdapterDashboard.ViewHolder>() {

    interface Listener{
        fun clickListener(location: Location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return mLocations.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val location = mLocations[p1]
        p0.mTvTitle.text = location.title
        p0.mTvText.text = location.detail
        Glide.with(mContext).load(location.urlImage).into(p0.mImvImg)
        p0.itemView.setOnClickListener {
            mListener.clickListener(location)
        }
    }

    class ViewHolder(item:View) : RecyclerView.ViewHolder(item){
        val mTvTitle = item.title!!
        val mImvImg = item.img!!
        val mTvText = item.text!!
    }

    fun dataChange(locations:MutableList<Location>){
        mLocations.clear()
        mLocations.addAll(locations)
        notifyDataSetChanged()
    }
}