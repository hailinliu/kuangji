package it.mbkj.tradecenter.bean

data class GetOrderBean(

    val buy_list: BuyList1
)

data class BuyList1(
    val current_page: Int,
    val data: List<DataX>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataX(
    val id: Int,
    val left_sum: String,
    val phone: String,
    val price: String,
    val scale: String,
    val sum: String,
    val status:Int,
    val user_name: String
)