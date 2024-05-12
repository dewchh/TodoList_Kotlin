package com.example.todocrud


import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var ed1: EditText? = null
    var ed2: EditText? = null
    var ed3: EditText? = null
    var b1: Button? = null
    var b2: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ed1 = findViewById(R.id.date)
        ed2 = findViewById(R.id.task)
        ed3 = findViewById(R.id.time)
        b1 = findViewById(R.id.bt1)
        b2 = findViewById(R.id.bt2)

        b2!!.setOnClickListener {
            val i: Intent = Intent(
                this,
                view::class.java
            )
            startActivity(i)
        }
        b1!!.setOnClickListener { insert() }
    }

    fun insert() {
        val date = ed1!!.text.toString().trim()
        val task = ed2!!.text.toString().trim()
        val time = ed3!!.text.toString().trim()

        if (date.isEmpty() || task.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show()
            return
        }

        try {
            val db: SQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath("todoDb"), null)
            db.execSQL("CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY AUTOINCREMENT,date VARCHAR,task VARCHAR,time VARCHAR)")
            val sql = "insert into records(date,task,time)values(?,?,?)"
            val statement = db.compileStatement(sql)
            statement.bindString(1, date)
            statement.bindString(2, task)
            statement.bindString(3, time)
            statement.execute()
            Toast.makeText(this, "Record added", Toast.LENGTH_LONG).show()
            ed1!!.setText("")
            ed2!!.setText("")
            ed3!!.setText("")
            ed1!!.requestFocus()
        } catch (ex: Exception) {
            Toast.makeText(this, "Record failed to add", Toast.LENGTH_LONG).show()
        }
    }

}