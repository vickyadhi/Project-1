package com.example.project1.modelclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("user_id")
    @Expose
    var userId: Int = 0

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("body")
    @Expose
    var body: String = ""

}