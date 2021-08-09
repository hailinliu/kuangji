package it.mbkj.personcenter.bean

data class RechargeRecordBean(

    val list: NewList
)

data class NewList(
    val current_page: Int,
    val data: List<Datad>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class Datad(
    val amount: String,
    val audit_status: Int,
    val coin_symbol: String,
    val create_time: String,
    val fee: String,
    val from_address: String,
    val phone: String,
    val transfer_status: Int
)