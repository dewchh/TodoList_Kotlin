package com.example.todocrud
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get the list of deleted tasks passed from the previous activity
        val deletedTasks = intent.getSerializableExtra("deletedTasks") as? ArrayList<Task> ?: ArrayList()

        val adapter = TaskAdapter()
        adapter.setData(deletedTasks)
        recyclerView.adapter = adapter
    }
}

