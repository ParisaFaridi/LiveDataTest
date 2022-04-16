package com.example.livedatatest

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg question: Question)

    @Query("SELECT * FROM Question")
    fun getAll(): LiveData<List<Question>>

    @Query("SELECT count(id) FROM Question")
    fun getCount():LiveData<Int>

    @Query("SELECT * FROM Question WHERE   id = (:id) Limit 1")
    fun getQuestionById(id:Int):Question

    @Delete
    fun delete(question : Question)

}