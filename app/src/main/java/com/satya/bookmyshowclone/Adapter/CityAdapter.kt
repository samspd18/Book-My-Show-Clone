package com.satya.bookmyshowclone.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satya.bookmyshowclone.databinding.CityLayoutBinding
import com.satya.bookmyshowclone.model.city.City
import com.satya.bookmyshowclone.ui.MainActivity
import com.squareup.picasso.Picasso

class CityAdapter: RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var city = mutableListOf<City>()

    fun setCities(city: Array<City>) {
        Log.e("city", city.count().toString() )
        this.city = city.toMutableList()
        notifyDataSetChanged()
    }
    class CityViewHolder(var binding: CityLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        var cityName: String = ""
        var cityImage: String = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityLayoutBinding.inflate(inflater,parent,false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val cityData = city[position]
        holder.binding.cityName.text = cityData.name
        val imageUrl = cityData.imageUrl

        Picasso.get()
            .load(imageUrl)
            .noFade()
            .into(holder.binding.cityImage)

        holder.cityName = cityData.name.toString()

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context,MainActivity::class.java)
            intent.putExtra("cityName",holder.cityName)
            intent.putExtra("time","first")
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return city.count()
    }
}