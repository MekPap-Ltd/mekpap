package com.mekpap.mekPap.payment

data class Payment(val amount:String,val phone:String,val customerName:String,val customerId:String){
    constructor():this("","","","")
}

data class Response(val AccessCode:String)