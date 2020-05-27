package com.example.sharedata

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

lateinit var adapter:MyRecyclerAdapter
val str= mutableListOf<List<String>>()
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//      Read CSV File and Save in a mutable Array  called csvData________
        val file = resources.openRawResource(R.raw.nsedaydata)
        val csvData = mutableListOf<SData>()
        val csvTitle = mutableListOf<String>()
        file.reader().forEachLine { e ->
            str.add(e.split(','))
        }
        fun ma() {
            var flag = 0
            for (each in str) {
                if (flag == 0) {
                    flag = 1
                    each.forEach { e ->
                        csvTitle.add(e)
                    }
                    continue
                }
                csvData.add(
                    SData(
                        each[0],
                        each[2].toDouble(),
                        each[3].toDouble(),
                        each[4].toDouble(),
                        each[5].toDouble(),
                        each[11].toInt()
                    )
                )
            }
        }
//      _________________________________________________________________
        ma()
//        setting RecyclerView Adapter___________________________________
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        mainRecylerView.layoutManager = layoutManager
        adapter = MyRecyclerAdapter(this, csvData,csvData)
        mainRecylerView.adapter = adapter

//      Setting Titles of the CSV file____________________________________
        titleView1.text = csvTitle[0]
        titleView2.text = csvTitle[2]
        titleView3.text = csvTitle[3]
        titleView4.text = csvTitle[4]
        titleView5.text = csvTitle[5]
        titleView6.text = csvTitle[11]

    }
//      creating Option menu_______________________________________________
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu?.findItem(R.id.searchView)
        val searchView: SearchView = item?.actionView as SearchView
//      Searching For Query_______________________________________
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
//      _____________________________________________________________________
}
