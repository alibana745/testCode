package com.ali.testcode

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Request(url: String, private val listener: OnListener) :
        AsyncTask<String, String, String>() {

    private val TAG = "Request"
    private val mUrl = URL(url)
    private val mConnection = mUrl.openConnection() as HttpURLConnection
    private var mMethod: String? = null
    private val json: JSONObject? = JSONObject()
    private var mRespondHeader: Int? = 0

    companion object {
        const val POST = "POST"
        const val GET = "GET"
        const val DELETE = "DELETE"
        const val PUT = "PUT"
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"
    }

    interface OnListener {
        fun respond(result: String, respondHeader: Int)
    }

    fun setHeader(key: String, value: String): Request {
        mConnection.setRequestProperty(key, value)
        return this
    }

    fun setBody(key: String, value: Any): Request {
        json?.put(key, value)
        return this
    }

    fun setMethod(method: String): Request {
        mMethod = method
        if (method == POST) {
            mConnection.doOutput = true
            mConnection.requestMethod = method
            mConnection.doInput = true
//            mConnection.setRequestProperty(Request.CONTENT_TYPE, Request.APPLICATION_JSON)
        } else {
            mConnection.requestMethod = method
        }
        mConnection.connectTimeout = 15000
        mConnection.readTimeout = 15000
        return this
    }

    override fun doInBackground(vararg params: String?): String {
        var result: String? = null
        try {
            Log.i(TAG,"Result $json")
//            if (mMethod == POST) {
                sendData()
//            }
            mConnection.connect()
            mRespondHeader = mConnection.responseCode
            //Log.d(TAG, "Respond Code $mRespondHeader")
            val tempStream: InputStream = mConnection.inputStream
            result = convertToString(tempStream)
        } catch (e: Exception) {
            e.stackTrace
        }
        Log.i(TAG,"Result $result")
        return result.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            listener.respond(result, mRespondHeader!!)
        }
    }

    private fun convertToString(inStream: InputStream): String {

        var result = ""
        val isReader = InputStreamReader(inStream)
        val bReader = BufferedReader(isReader)
        var tempStr: String?

        try {

            while (true) {
                tempStr = bReader.readLine()
                if (tempStr == null) {
                    break
                }
                result += tempStr
            }
        } catch (Ex: Exception) {
            Log.e(TAG, "Error in convertToString " + Ex.printStackTrace())
        }
        return result
    }

    private fun sendData() {
        var wr: BufferedWriter? = null
        try {
            val os = mConnection.outputStream
            wr = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            wr.write(json.toString())
            wr.flush()
        } catch (e1: UnsupportedEncodingException) {
            e1.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        } finally {
            if (wr != null)
                try {
                    wr.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

        }

    }
}