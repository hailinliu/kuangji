package it.mbkj.personcenter.bean

data class TranferRecordBean(

    val list: Bean
)

data class Bean(
    val current_page: Int,
    val data: List<DataQ>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataQ(
    val create_time: String,
    val money_str: String,
    val phone: String,
    val sum: String,
    val type_msg: String
)