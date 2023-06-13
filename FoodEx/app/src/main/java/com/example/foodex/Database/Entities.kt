package com.example.foodex.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="users")
class UserEntity (
    @PrimaryKey val username:String,
    @ColumnInfo(name="email_id") val emailId:String,
    @ColumnInfo(name="mobile_number") val mobileNumber:String,
    @ColumnInfo(name="delivery_address") val deliveryAddress:String,
    @ColumnInfo(name="password") val password:String,

    )