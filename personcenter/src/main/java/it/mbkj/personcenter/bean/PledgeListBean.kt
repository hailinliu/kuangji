package it.mbkj.personcenter.bean

data class PledgeListBean(
    val pledge_list: List<Pledge>
)

data class Pledge(
    val all_time: String,
    val create_time: Int,
    val have_out: Int,
    val id: Int,
    val income: String,
    val max_get: Int,
    val pledge_name: String,
    val price: String,
    val status: Int,
    val sum: Int
)