package com.example.foodex.model

import com.example.foodex.activity.ForgotPassword

data class User(
    val username:String,
    val emailId:String,
    val mobileNumber:String,
    val deliveryAddress :String,
    val password: String
)