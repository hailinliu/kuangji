package it.mbkj.personcenter.bean

data class OrderDetailBean(

    val order_msg: OrderMsg
)

data class OrderMsg(
    val all_time: String,
    val create_time: String,
    val end_time: String,
    val id: Int,
    val income: String,
    val pledge_id: Int,
    val pledge_name: String,
    val price: String,
    val status: Int,
    val user_id: Int
)