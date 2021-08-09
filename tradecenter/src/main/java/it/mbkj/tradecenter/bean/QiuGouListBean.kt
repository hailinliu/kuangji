package it.mbkj.tradecenter.bean

data class QiuGouListBean(

    val buy_list: BuyList
)

data class BuyList(
    val current_page: Int,
    val data: List<DataS>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataS(
    val id: Int,
    val left_sum: String,
    val phone: String,
    val price: String,
    val scale: String,
    val sum: String,
    val user_name: String
)