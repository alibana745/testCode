package com.ali.testcode.dashboard

import com.ali.testcode.TouristDestination
import com.ali.testcode.Request
import org.json.JSONObject
import javax.net.ssl.HttpsURLConnection

class PresenterDashboard(val view:ContractDashboard.View) : ContractDashboard.Presenter {
    override fun getDataLocation() {
        view.progressBar(true)
        val request = Request("http://erporate.com/bootcamp/jsonBootcamp.php", object : Request.OnListener {
            override fun respond(result: String, respondHeader: Int) {
                val json = JSONObject(result)
                when (respondHeader) {
                    HttpsURLConnection.HTTP_OK -> {
                        val touristDestinations: MutableList<TouristDestination> = ArrayList()
                        val jsonArray = json.getJSONArray("data")
                        for (i in 0 until jsonArray.length() step 1) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val location = TouristDestination(jsonObject.optString("nama_pariwisata"),
                                    jsonObject.optString("gambar_pariwisata"),
                                    jsonObject.optString("detail_pariwisata"),
                                    jsonObject.optString("alamat_pariwisata"))
                            touristDestinations.add(location)
                        }
                        view.progressBar(false)
                        view.setData(touristDestinations)
                    }
                }
            }
        })
        request.setMethod(Request.GET).execute()
    }

}