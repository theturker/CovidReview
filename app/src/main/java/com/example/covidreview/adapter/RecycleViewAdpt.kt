package com.example.covidreview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covidreview.R
import com.example.covidreview.model.CovidModel
import com.example.covidreview.service.CovidApi
import kotlinx.android.synthetic.main.menudetail.view.*


class RecycleViewAdpt(private val covidList : ArrayList<CovidModel>, private val listener: Listener) : RecyclerView.Adapter<RecycleViewAdpt.RowHolder>() {
    interface Listener{
        fun onItemClick(covidModel: CovidModel)
    }
    class RowHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(covidModel: CovidModel, position: Int, listener : Listener){
            itemView.setOnClickListener{
                listener.onItemClick(covidModel)
            }
            //itemView.txtCountry.text = covidModel.country
            itemView.txtCountry.text = covidModel.name
            itemView.txtCode.text = covidModel.code
            itemView.txtconfirmed.text = covidModel.confirmed
            itemView.txtrecovered.text = covidModel.recovered
            itemView.txtcritical.text = covidModel.critical
            itemView.txtdeath.text = covidModel.deaths
            itemView.txtlatitude.text = covidModel.latitude
            itemView.txtlongitude.text = covidModel.longitude
            itemView.txtlastChange.text = covidModel.lastChange
            itemView.txtlastUpdate.text = covidModel.lastUpdate
            //itemView.txtCountries.text = covidModel.name

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menudetail,parent,false)

        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(covidList[position],position,listener)

    }

    override fun getItemCount(): Int {
        return covidList.count()

    }
}