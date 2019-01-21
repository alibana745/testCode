package com.ali.testcode.dashboard

import com.ali.testcode.Location
import com.ali.testcode.Request
import org.json.JSONObject

class PresenterDashboard(val view:ContractDashboard.View) : ContractDashboard.Presenter {
    override fun getDataLocation() {
        val request = Request("http://erporate.com/bootcamp/jsonBootcamp.php", object : Request.OnListener{
            override fun respond(result: String, respondHeader: Int) {
                val json = JSONObject(result)
                val locations : MutableList<Location> = ArrayList()
                val jsonArray = json.getJSONArray("data")
                for (i in 0 until jsonArray.length() step 1){
                    val jsonObject = jsonArray.getJSONObject(i)
                    val location = Location(jsonObject.optString("nama_pariwisata"),
                            jsonObject.optString("gambar_pariwisata"),
                            jsonObject.optString("detail_pariwisata"),
                            jsonObject.optString("alamat_pariwisata"))
                    locations.add(location)
                }
                view.setData(locations)
            }
        })
        request.setMethod(Request.GET).execute()
    }

}