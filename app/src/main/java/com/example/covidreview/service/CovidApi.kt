package com.example.covidreview.service

import com.example.covidreview.model.CovidModel
import io.reactivex.Observable
import retrofit2.http.*

interface CovidApi {
    fun countriesName(string: String)
    fun onDataReceived(myData: String?)


    //GET, POST, UPDATE, DELETE
    @Headers("x-rapidapi-key: 461346ee55msh81e0732f86abd83p11893ejsn96924de4cd18")
    @GET("/country")
    fun getData(@Query("name") name : String) : Observable<List<CovidModel>>
    //@GET("/country?name={url}")

    @Headers("x-rapidapi-key: 461346ee55msh81e0732f86abd83p11893ejsn96924de4cd18")
    @GET("/help/countries")
    fun getListOfCountries() : Observable<List<CovidModel>>

}