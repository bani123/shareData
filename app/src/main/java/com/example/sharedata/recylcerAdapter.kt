package com.example.sharedata

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview.view.*
import java.util.*

class MyRecyclerAdapter(
    private val context: Context,
    private val csvData: MutableList<SData>,
    private val csvDataFull:MutableList<SData>
) : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>(),Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return csvData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<SData>()

                if (constraint.toString().isEmpty()) {
                    filteredList.addAll(csvDataFull)
                    println(csvDataFull)
                } else {
                    for (each in csvDataFull) {
                        if (each.symbol.toLowerCase(Locale.ROOT).contains(
                                constraint.toString().toLowerCase(Locale.ROOT)
                            )
                        ) {
                            filteredList.add(each)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                csvData.clear()
                csvData.addAll(results?.values as MutableList<SData>)
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val csvD = csvData[position]
        holder.setData(csvD)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(csvD: SData) {
            itemView.textView1.text = csvD.symbol
            itemView.textView2.text = csvD.open.toString()
            itemView.textView3.text = csvD.high.toString()
            itemView.textView4.text = csvD.low.toString()
            itemView.textView5.text = csvD.close.toString()
            itemView.textView6.text = csvD.totalTrade.toString()
        }
    }
}