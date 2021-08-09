package it.mbkj.tradecenter.bean

data class BuyListBean(

    val buy_list: BuyList2
)

data class BuyList2(
    val current_page: Int,
    val data: List<DataX1>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataX1(
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