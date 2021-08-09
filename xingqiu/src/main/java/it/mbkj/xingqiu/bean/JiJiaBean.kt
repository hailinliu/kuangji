package it.mbkj.xingqiu.bean

data class JiJiaBean(
    val code: Int,
    val data: Data,
    val msg:String,
    val error_code:Int
)

data class Data(
    val machine_list: MachineList
)

data class MachineList(
    val current_page: Int,
    val data: List<DataX>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataX(
    val all_time: Int,
    val can_buy: Int,
    val gif_url: Any,
    val id: Int,
    val img_url: String,
    val income: String,
    val max_get: Int,
    val name: String,
    val price: String,
    val status: Int,
    val machine_sum:String
)




