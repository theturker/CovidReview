package com.example.covidreview.model

import com.google.gson.annotations.SerializedName

data class CovidModel(
    //Tanımlı modelde ki name ile kod modeli aynı ise SerializedName kullanmaya gerek yok.
    val country : String,
    val code : String,
    val confirmed : String,
    val recovered : String,
    val critical : String,
    val deaths : String,
    val latitude : String,
    val longitude : String,
    val lastChange : String,
    val lastUpdate : String,
    val name : String)