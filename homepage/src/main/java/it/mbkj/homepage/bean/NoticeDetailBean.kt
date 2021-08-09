package it.mbkj.homepage.bean

data class NoticeDetailBean(

    val notice_msg: NoticeMsg
)

data class NoticeMsg(
    val content: String,
    val create_time: Long,
    val id: Int,
    val introduce: String,
    val notice_type: Any,
    val title: String,
    val update_time: Long
)