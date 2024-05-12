package com.example.todocrud

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class edit : AppCompatActivity() {
    // Declare a list to store deleted tasks
    private val deletedTasks = ArrayList<Task>()

    var ed1: EditText? = null
    var ed2: EditText? = null
    var ed3: EditText? = null
    var ed4: EditText? = null
    var b1: Button? = null
    var b2: Button? = null
    var b3: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        ed1 = findViewById(R.id.date)
        ed2 = findViewById(R.id.task)
        ed3 = findViewById(R.id.time)
        ed4 = findViewById(R.id.id)
        b1 = findViewById(R.id.bt1)
        b2 = findViewById(R.id.bt2)
        b3 = findViewById(R.id.bt3)
        val i = intent
        val t1 = i.getStringExtra("id").toString()
        val t2 = i.getStringExtra("date").toString()
        val t3 = i.getStringExtra("task").toString()
        val t4 = i.getStringExtra("time").toString()
        ed4!!.setText(t1)
        ed1!!.setText(t2)
        ed2!!.setText(t3)
        ed3!!.setText(t4)
        b2!!.setOnClickListener(View.OnClickListener { Delete() })
        b3!!.setOnClickListener(View.OnClickListener {
            val i = Intent(
                this,
                view::class.java
            )
            i.putExtra("deletedTasks", deletedTasks)
            startActivity(i)
        })
        b1!!.setOnClickListener(View.OnClickListener { Edit() })
    }

    fun Delete() {
        try {
            val id = ed4!!.text.toString()
            val db = openOrCreateDatabase("todoDb", Context.MODE_PRIVATE, null)
            val sql = "delete from records where id = ?"
            val statement = db.compileStatement(sql)
            statement.bindString(1, id)
            statement.execute()
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_LONG).show()
            ed1!!.setText("")
            ed2!!.setText("")
            ed3!!.setText("")
            ed1!!.requestFocus()

            // Add the deleted task to the list
            val deletedTask = Task().apply {
                this.id = id
                date = ed1!!.text.toString()
                task = ed2!!.text.toString()
                time = ed3!!.text.toString()
            }
            deletedTasks.add(deletedTask)

        } catch (ex: Exception) {
            Toast.makeText(this, "Record Fail", Toast.LENGTH_LONG).show()
        }
    }

    fun Edit() {
        try {
            val date = ed1!!.text.toString()
            val task = ed2!!.text.toString()
            val time = ed3!!.text.toString()
            val id = ed4!!.text.toString()
            val db = openOrCreateDatabase("todoDb", Context.MODE_PRIVATE, null)
            val sql = "update records set date = ?,task=?,time=? where id= ?"
            val statement = db.compileStatement(sql)
            statement.bindString(1, date)
            statement.bindString(2, task)
            statement.bindString(3, time)
            statement.bindString(4, id)
            statement.execute()
            Toast.makeText(this, "Record Updated", Toast.LENGTH_LONG).show()
            ed1!!.setText("")
            ed2!!.setText("")
            ed3!!.setText("")
            ed1!!.requestFocus()
        } catch (ex: Exception) {
            Toast.makeText(this, "Record Fail", Toast.LENGTH_LONG).show()
        }
    }
}