package com.example.foodex.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodex.activity.ForgotPassword

@Dao
interface UserDao {
    @Insert
    fun addUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("select * from users where username=:username and password=:password")
    fun getUser(username:String,password:String):UserEntity
}