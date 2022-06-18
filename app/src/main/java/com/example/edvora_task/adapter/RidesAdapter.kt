package com.example.edvora_task.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.edvora_task.R
import com.example.edvora_task.model.RidesItem
import kotlinx.android.synthetic.main.list_item_ride.view.*
import kotlin.math.abs

class RidesAdapter: RecyclerView.Adapter<RidesAdapter.RidesItemViewHolder>(){
    inner class RidesItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<RidesItem>(){
        override fun areItemsTheSame(oldItem: RidesItem, newItem: RidesItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RidesItem, newItem: RidesItem): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RidesItemViewHolder {
        return RidesItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_ride,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RidesItemViewHolder, position: Int) {
        val ride = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(ride.map_url).into(ivRideImage)
            tvCityName.text = ride.city
            tvDate.text = "Date : ${ride.date}"
            tvRideId.text = "Ride Id : "+ride.id.toString()
            tvOrigin.text = "Origin Station : "+ride.origin_station_code.toString()
            tvStateName.text = ride.state
            tvStationPath.text = "Station Path : " +ride.station_path!!.joinToString(" ")
            tvDistance.text = "Disance : "+ getDistance(ride.station_path).toString()
        }
    }
    fun getDistance(stationPath:List<Int?>?) : Int{
        var min= abs(48- (stationPath?.get(0) ?: 48))
        if (stationPath != null) {
            for(i in stationPath)
                if(min > abs(48- i!!))
                    min=abs(48- i)
        }
        return min
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}