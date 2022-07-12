package com.example.assignmentapplication.Todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignmentapplication.R
import com.example.assignmentapplication.data.local.TodoListDatabase
import com.example.assignmentapplication.data.local.models.Todo
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.text.SimpleDateFormat
import java.util.*

class AddTodoActivity: AppCompatActivity(){

    private var todoDatabase: TodoListDatabase? = null
    private var priority = 1
    var current_date=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        getCurrentDate()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        todoDatabase = TodoListDatabase.getInstance(this)

        val title = intent.getStringExtra("title")
        if (title == null || title == ""){
            add_todo.setOnClickListener{
                if(title_ed.text.toString()!="" && detail_ed.text.toString()!=""){
                    val todo = Todo(title_ed.text.toString(), current_date)
                    todo.detail = detail_ed.text.toString()
                    todoDatabase!!.getTodoDao().saveTodo(todo)
                    finish()
                }else{
                    Toast.makeText(this, "Must add Task Title and Task Details.", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            val detail = intent.getStringExtra("detail")
            add_todo.text = getString(R.string.update)
            val tId = intent.getIntExtra("tId", 0)
            title_ed.setText(title)
            detail_ed.setText(detail)
            add_todo.setOnClickListener {
                if(title_ed.text.toString()!="" && detail_ed.text.toString()!=""){
                    val todo = Todo(title_ed.text.toString(), current_date, tId)
                    todo.detail = detail_ed.text.toString()
                    todoDatabase!!.getTodoDao().updateTodo(todo)
                    finish()
                }else{
                    Toast.makeText(this, "Must add Task Title and Task Details.", Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home){
            startActivity(Intent(this, TodoActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentDate() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val month_date = SimpleDateFormat("MMMM")
        cal[Calendar.MONTH] = month
        val month_name: String = month_date.format(cal.time)
        current_date = month_name+" "+day.toString()+", "+year.toString()
    }

}