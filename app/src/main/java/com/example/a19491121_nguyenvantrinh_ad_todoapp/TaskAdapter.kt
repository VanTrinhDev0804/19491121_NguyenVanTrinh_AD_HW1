package com.example.a19491121_nguyenvantrinh_ad_todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasksList: MutableList<DataTask>)
    : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        var currentTask = tasksList[position]
        holder.name.text = currentTask.name
        if(currentTask.status == "true"){
            holder.status.text = "Completed"
        }
        else{
            holder.status.text = " Not Completed"
        }

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
      return tasksList.size
    }
    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.name_task)
        var status : TextView = itemView.findViewById(R.id.status)
    }
}
