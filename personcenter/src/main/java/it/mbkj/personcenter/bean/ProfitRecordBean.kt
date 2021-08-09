package it.mbkj.personcenter.bean

 data class ProfitRecordBean(

    val list: FirstList,
    val type_list: List<String>
)

data class FirstList(
    val current_page: Int,
    val data: List<DataX1>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataX1(
    val create_time: Long,
    val id: Int,
    val money_str: String,
    val other: String,
    val sum: String,
    val type_msg: String,
    val user_id: Int
)