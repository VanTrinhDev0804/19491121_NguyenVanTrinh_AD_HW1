package com.example.a19491121_nguyenvantrinh_ad_todoapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerTaskView: RecyclerView
    private lateinit var tasksList: ArrayList<TaskItem>
    lateinit var statusTasks :Array<Int>
    lateinit var nameTask :Array<String>
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
        tasksList = arrayListOf<TaskItem>()
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
            writeOrderOnDataBase(m_Text, false)
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }


    private fun writeOrderOnDataBase(
        nameTask:String,
        status: Boolean,
    ) {
        database = FirebaseDatabase.getInstance().reference
        var task = TaskItem( nameTask, status)
        database.child("Task").push().child(nameTask).setValue(task)

    }

    private fun getTaskData() {
            var tasksitems =TaskItem("call mom",false)
            var tasksitems2 =TaskItem("an com voi doi tac",false)
            tasksList.add(tasksitems)
            tasksList.add(tasksitems2)
        recyclerTaskView.adapter = TaskAdapter(tasksList)
    }

}