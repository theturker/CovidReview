package com.example.covidreview.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidreview.R
import com.example.covidreview.adapter.RecycleViewAdpt
import com.example.covidreview.model.CovidModel
import com.example.covidreview.service.CovidApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menudetail.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecycleViewAdpt.Listener {
    private var covidModels : ArrayList<CovidModel>? = null
    private var recyclerViewAdap : RecycleViewAdpt? = null

    //Disposable
    private var compositeDisposable : CompositeDisposable? = null

    private var booleanDataInf:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        compositeDisposable = CompositeDisposable()

        loadData()

        if (booleanDataInf)
            linearLyAll.visibility = View.VISIBLE
    }

    private fun loadData(){
        val retrofit =
            Retrofit.Builder().baseUrl("https://covid-19-data.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CovidApi::class.java)

        compositeDisposable?.add(retrofit.getListOfCountries()
                .subscribeOn(Schedulers.io())//gelen veriyi arka planda dinliyor
                .observeOn(AndroidSchedulers.mainThread())//mainthread tarafına işliyor
                .subscribe(this :: handleResponse))//handleResponse'a aktarıyor

    }

    private fun postData(string: String){
        booleanDataInf = true
        val retrofit =
                Retrofit.Builder().baseUrl("https://covid-19-data.p.rapidapi.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build().create(CovidApi::class.java)

        compositeDisposable?.add(retrofit.getData(string)
                .subscribeOn(Schedulers.io())//gelen veriyi arka planda dinliyor
                .observeOn(AndroidSchedulers.mainThread())//mainthread tarafına işliyor
                .subscribe(this :: handleResponse))//handleResponse'a aktarıyor

    }

    private fun handleResponse(covidList: List<CovidModel>) {
        covidModels = ArrayList(covidList)
        covidModels?.let {
            recyclerViewAdap = RecycleViewAdpt(covidModels!!, this@MainActivity)
            recyclerView.adapter = recyclerViewAdap

        }
    }

    override fun onItemClick(covidModel: CovidModel) {
        Toast.makeText(this, "Tıklandı : ${covidModel.country}",Toast.LENGTH_LONG).show()
        //covidModel.name.
        postData(covidModel.name)


        if (booleanDataInf)
            linearLyAll.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}