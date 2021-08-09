package it.mbkj.personcenter.bean

 data class ZiChanBean(
    val code: Int,
    val data: Data,
    val msg:String,
    val deal_scale: String,
    val transfer_scale: String,
    val withdraw_scale: String,
    val error_code:Int,
    val request_url:String,
)

data class Data(
    val money: String,
    val score: String
)