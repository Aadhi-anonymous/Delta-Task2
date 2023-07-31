package com.example.deltatask2


import com.google.gson.annotations.SerializedName
data class tips(
    @SerializedName("tips")
    var tips:ArrayList<String> = arrayListOf()
)
