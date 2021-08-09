package it.mbkj.personcenter.bean

data class TiBiRecordBean(

    val list: List<TIBI>,
    val page: Int,
    val last_page: Int,
)

data class TIBI(
    val amount: Int,
    val audit_status: Int,
    val coin_symbol: String,
    val create_time: String,
    val fee: Int,
    val from_address: String,
    val phone: String,
    val to_address: String,
    val transfer_status: Int
)