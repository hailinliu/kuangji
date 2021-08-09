package it.mbkj.login.bean

data class LoginBean(
    val code: Int,
    val msg: String,
    val error_code:Int,
    val session_id: String
)