package it.mbkj.homepage.bean

import java.io.Serializable


data class NoticeBean(
    val code: Int,
    val data: Data,
    val msg:String,
    val error_code:Int
):Serializable{
    data class Data(
        val notice_list: ArrayList<Notice>
    ):Serializable{
        data class Notice(
            val content: String,
            val create_time: Long,
            val id: Int,
            val introduce: String,
            val notice_type: Any,
            val title: String,
            val update_time: Long
        ):Serializable
    }


}

