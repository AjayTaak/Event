package com.example.event

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RecyclerAdapter(private val eventList : ArrayList<Event> ) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_row,
        parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = eventList[position]

//        val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
//        val date = LocalDate.parse(currentItem.startTime.toString(), secondApiFormat)
//
//        Log.d("parseTesting", date.dayOfWeek.toString())
//        Log.d("parseTesting", date.month.toString())

        holder.eventTitle.text = currentItem.title
        //holder.eventStatus.text = currentItem.endTime - currentItem.startTime
        holder.eventDesciption.text = currentItem.description

        holder.cardLay.setOnClickListener(){
            Log.d("parseTesting11", currentItem.title)
        }


    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val eventTitle : TextView = itemView.findViewById(R.id.tvEventTitle)
        val eventStatus : TextView = itemView.findViewById(R.id.tvEventStatus)
        val eventDesciption : TextView = itemView.findViewById(R.id.tvEventDescription)
        val cardLay : RelativeLayout = itemView.findViewById(R.id.cardLayout)
    }

}