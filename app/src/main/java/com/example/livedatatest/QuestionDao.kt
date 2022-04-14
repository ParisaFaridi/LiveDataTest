package com.example.livedatatest

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {

    @Insert
    fun addQuestion(vararg question: Question)

    @Query("SELECT * FROM Question")
    fun getAll(): List<Question>

    @Query("SELECT count(*) FROM Question")
    fun getCount():LiveData<Int>

    @Query("SELECT * FROM Question WHERE   id = (:id) Limit 1")
    fun getQuestionById(id:Int):Question

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg questions: Question)

    @Delete
    fun delete(question : Question)

}