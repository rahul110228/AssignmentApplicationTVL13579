package com.example.assignmentapplication.Todo

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentapplication.R
import com.example.assignmentapplication.data.local.TodoListDatabase
import com.example.assignmentapplication.data.local.models.Todo
import kotlinx.android.synthetic.main.activity_main.*

class TodoActivity : AppCompatActivity(), TodoAdapter.OnTodoItemClickedListener{

    private var todoDatabase: TodoListDatabase? = null
    private var todoAdapter: TodoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        todoDatabase = TodoListDatabase.getInstance(this)
        todoAdapter = TodoAdapter()
        todoAdapter?.setTodoItemClickedListener(this)

        add_todo.setOnClickListener {
            startActivity(Intent(this, AddTodoActivity::class.java))
        }


    }


    override fun onResume() {
        super.onResume()
        todoAdapter?.todoList=todoDatabase?.getTodoDao()?.getTodoList()
        todo_rv.adapter = todoAdapter
        todo_rv.layoutManager = LinearLayoutManager(this)
        todo_rv.hasFixedSize()
    }

    override fun onTodoItemClicked(todo: Todo) {
        val intent = Intent(this, AddTodoActivity::class.java)
        intent.putExtra("tId", todo.tId)
        intent.putExtra("title", todo.title)
        intent.putExtra("date", todo.date)
        intent.putExtra("detail", todo.detail)
        startActivity(intent)
    }

    override fun onTodoItemLongClicked(todo: Todo) {
        val alertDialog = AlertDialog.Builder(this)
                .setItems(R.array.dialog_list, DialogInterface.OnClickListener { dialog, which ->
                    if (which==0){
                        val intent = Intent(this@TodoActivity, AddTodoActivity::class.java)
                        intent.putExtra("tId", todo.tId)
                        intent.putExtra("title", todo.title)
                        intent.putExtra("date", todo.date)
                        intent.putExtra("detail", todo.detail)
                        startActivity(intent)
                    }else{
                        todoDatabase?.getTodoDao()?.removeTodo(todo)
                        onResume()
                    }
                    dialog.dismiss()
                })
                .create()
        alertDialog.show()
    }
}
