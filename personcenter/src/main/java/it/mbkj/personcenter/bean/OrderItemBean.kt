package it.mbkj.personcenter.bean

data class OrderItemBean(
    val pledge_order_list: PledgeOrderList
)

data class PledgeOrderList(
    val current_page: Int,
    val data: List<DataX>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataX(
    val all_time: String,
    val create_time: Long,
    val end_time: Long,
    val id: Int,
    val income: String,
    val pledge_id: Int,
    val pledge_name: String,
    val price: String,
    val status: Int,
    val user_id: Int
)