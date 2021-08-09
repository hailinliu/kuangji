package it.mbkj.lib.base

data class BaseBean<Data>(
    val code:Int,
    val msg:String,
    val error_code:Int,
    val request_url:String,
    val data:Data
)