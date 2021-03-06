package com.learn2crack.retrofitkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import br.com.alexf.ceep.retrofit.client.NoteWebClient
import com.google.gson.JsonArray

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

import com.learn2crack.retrofitkotlin.network.RequestInterface
import com.learn2crack.retrofitkotlin.adapter.DataAdapter
import com.learn2crack.retrofitkotlin.model.Android

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray


class MainActivity : AppCompatActivity(), DataAdapter.Listener {

    private val TAG = MainActivity::class.java.simpleName

    private val BASE_URL = "https://learn2crack-json.herokuapp.com"

    private var mCompositeDisposable: CompositeDisposable? = null

    private lateinit var mAndroidArrayList: List<Android>

    private var mAdapter: DataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCompositeDisposable = CompositeDisposable()

        initRecyclerView()

        loadJSON()
    }

    private fun initRecyclerView() {

        rv_android_list.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_android_list.layoutManager = layoutManager
    }

    private fun loadJSON() {

        mAndroidArrayList = ArrayList<Android>()

        NoteWebClient().list({

            Log.e("TAG","sas"+it)

//            mAndroidArrayList = ArrayList(it)

            var jsonArray = JSONArray(it)
            for (jsonIndex in 0..(jsonArray.length() - 1)) {



                (mAndroidArrayList as ArrayList<Android>).add(Android(
                        jsonArray.getJSONObject(jsonIndex).getString("name"),
                        jsonArray.getJSONObject(jsonIndex).getString("version"),
                        jsonArray.getJSONObject(jsonIndex).getString("apiLevel")

                ))


//                Log.d("JSON", jsonArray.getJSONObject(jsonIndex).getString("title"))
            }


            mAdapter = DataAdapter(mAndroidArrayList, this)

            rv_android_list.adapter = mAdapter

//            notes.addAll(it)
//            configureList()
        }, {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        })


       /* val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface::class.java)

        mCompositeDisposable?.add(requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError))*/

    }

    private fun handleResponse(androidList: List<Android>) {

        mAndroidArrayList = ArrayList(androidList)
        mAdapter = DataAdapter(mAndroidArrayList!!, this)

        rv_android_list.adapter = mAdapter
    }

    private fun handleError(error: Throwable) {

        Log.d(TAG, error.localizedMessage)

        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(android: Android) {

        Toast.makeText(this, "${android.name} Clicked !", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear()
    }
}
