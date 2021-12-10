package com.example.a19491121_nguyenvantrinh_ad_todoapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerTaskView: RecyclerView
    private val data: MutableList<DataTask> = mutableListOf()
    private var m_Text :String = "";

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerTaskView = findViewById(R.id.recyclerViewTask)
        val btnAdd = findViewById<ImageView>(R.id.btnAdd)
        btnAdd.setOnClickListener{
            showdialog()
        }

        recyclerTaskView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerTaskView.setHasFixedSize(true)
        getTaskData()

    }

    private fun showdialog(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Add New Task")

// Set up the input
        val input = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
             m_Text = input.text.toString()
            writeOrderOnDataBase(m_Text, "false")
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }


    private fun writeOrderOnDataBase(
        nameTask:String,
        status: String,
    ) {
        database = FirebaseDatabase.getInstance().reference
        var task = DataTask(nameTask, status)
//        database.child("Task").push().child(id.toString()).setValue(task)
        database.child("Task").push().setValue(task)

    }

    private fun getTaskData() {
        database = FirebaseDatabase.getInstance().getReference("Task")
        database.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tasks = snapshot.getValue(DataTask::class.java)
                data.add(tasks!!)

                recyclerTaskView.adapter?.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        recyclerTaskView.adapter = TaskAdapter(data)
    }

}