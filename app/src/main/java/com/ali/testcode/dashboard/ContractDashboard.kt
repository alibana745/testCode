package com.ali.testcode.dashboard

import com.ali.testcode.Location

interface ContractDashboard {
    interface Presenter{
        fun getDataLocation()
    }

    interface View {
        fun setData(Locations:MutableList<Location>)
    }
}