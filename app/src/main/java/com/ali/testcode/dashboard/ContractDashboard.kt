package com.ali.testcode.dashboard

import com.ali.testcode.TouristDestination

interface ContractDashboard {
    interface Presenter{
        fun getDataLocation()
    }

    interface View {
        fun setData(touristDestinations:MutableList<TouristDestination>)
        fun progressBar(status:Boolean)
    }
}