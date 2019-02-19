package com.ali.testcode

import java.io.Serializable

data class TouristDestination(var title:String, var urlImage:String, var detail:String, var address:String)
    : Serializable