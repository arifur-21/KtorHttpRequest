package com.example.ktorhttprequest.util

import com.example.ktorhttprequest.data.Post

sealed class ApiState{
    object Empty: ApiState()
    class  Failure(val msg: Throwable): ApiState()
    class Success(val data: List<Post>): ApiState()
    object Loading: ApiState()
}
