package com.example.roomlibraryexample;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Query("SELECT * FROM expense")
    List<Expense> getAllExpense();

    @Insert
    void addTransaction(Expense exp);

    @Update
    void updateTransaction(Expense exp);

    @Delete
    void deleteTransaction(Expense exp);

}
