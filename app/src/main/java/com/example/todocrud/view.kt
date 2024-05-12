package com.example.todocrud
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class view : AppCompatActivity() {
    private var lst1: ListView? = null
    private var titles = ArrayList<String?>()
    private var arrayAdapter: ArrayAdapter<String?>? = null
    private var allTasks = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val addButton = findViewById<Button>(R.id.btAddTask)

        addButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val recyclerViewButton = findViewById<Button>(R.id.btViewRecyclerView)

        recyclerViewButton.setOnClickListener {
            val intent = Intent(this, RecyclerViewActivity::class.java)
            startActivity(intent)
        }




        val db = openOrCreateDatabase("todoDb", Context.MODE_PRIVATE, null)
        lst1 = findViewById(R.id.lst1)
        val c = db.rawQuery("select * from records", null)
        val id = c.getColumnIndex("id")
        val date = c.getColumnIndex("date")
        val task = c.getColumnIndex("task")
        val time = c.getColumnIndex("time")
        titles.clear()
        allTasks.clear()
        if (c.moveToFirst()) {
            do {
                val ta = Task()
                ta.id = c.getString(id)
                ta.date = c.getString(date)
                ta.task = c.getString(task)
                ta.time = c.getString(time)
                allTasks.add(ta)
                titles.add("${c.getString(id)} \t\t${c.getString(task)}  \n\t\t\t Date: ${c.getString(date)} \t\t Time: ${c.getString(time)}")
            } while (c.moveToNext())
        }
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            titles
        )
        lst1?.adapter = arrayAdapter
        lst1?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val ta: Task = allTasks[position]
            val intent = Intent(
                this@view,
                edit::class.java
            )
            intent.putExtra("id", ta.id)
            intent.putExtra("date", ta.date)
            intent.putExtra("task", ta.task)
            intent.putExtra("time", ta.time)
            startActivity(intent)
        }

        val searchButton = findViewById<Button>(R.id.search_button)
        val searchEditText = findViewById<EditText>(R.id.task)

        searchButton.setOnClickListener {
            val searchQuery = searchEditText.text.toString().trim()
            search(searchQuery)
        }
    }

    private fun search(query: String) {
        val filteredTitles = ArrayList<String?>()
        val filteredTasks = ArrayList<Task>()

        for (i in allTasks.indices) {
            val task = allTasks[i]
            if (task.date?.contains(query, ignoreCase = true) == true ||
                task.task?.contains(query, ignoreCase = true) == true ||
                task.time?.contains(query, ignoreCase = true) == true
            ) {
                filteredTasks.add(task)
                filteredTitles.add(titles[i])
            }
        }

        if (filteredTasks.isNotEmpty()) {
            arrayAdapter?.clear()
            arrayAdapter?.addAll(filteredTitles)
            arrayAdapter?.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
            // If no tasks match the search query, you may want to clear the ListView or display a message.
            arrayAdapter?.clear()
            arrayAdapter?.notifyDataSetChanged()
        }
    }


}