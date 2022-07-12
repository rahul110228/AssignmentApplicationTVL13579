package com.example.assignmentapplication.data.local.models

import androidx.room.*

@Entity(tableName = "todo")
class Todo(
        @ColumnInfo(name = "todo_title")
        var title:String = "",
        @ColumnInfo(name = "todo_date")
        var date: String = "",
        @PrimaryKey(autoGenerate = true) var tId: Int = 0){
        var detail: String = ""
}