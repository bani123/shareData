package com.example.sharedata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview.view.*
import java.util.*

class MyRecyclerAdapter(
    private val context: Context,
    private val csvData: MutableList<SData>,
    private var csvDataFilter:MutableList<SData>
) : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val csvD = csvDataFilter[position]
        holder.setData(csvD)
    }
    override fun getItemCount(): Int {
        return csvDataFilter.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                csvDataFilter = if (constraint.toString().isEmpty()) {
                    csvData
                } else {
                    val resultList = mutableListOf<SData>()
                    for (each in csvData) {
                        if (each.symbol.toLowerCase(Locale.ROOT).contains(
                                constraint.toString().toLowerCase(Locale.ROOT)
                            )
                        ) {
                            resultList.add(each)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = csvDataFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                csvDataFilter=results?.values as MutableList<SData>
                notifyDataSetChanged()
            }
        }
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