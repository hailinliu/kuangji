package it.mbkj.tradecenter.bean

 data class SellListBean(

    val sell_list: SellList
)

data class SellList(
    val current_page: Int,
    val data: List<DataT>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataT(
    val buy_phone: String,
    val create_time: String,
    val id: Int,
    val order_num: String,
    val price: String,
    val scale: String,
    val sell_phone: String,
    val sum: String,
    val update_time: String
)