package it.mbkj.personcenter.bean

data class JiJiaListBean(
    val on_machine_list: OnMachineList
)
data class OnMachineList(
    val current_page: Int,
    val data: List<DataT>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)

data class DataT(
    val all_time: Int,
    val begin_time: String,
    val create_time: String,
    val end_time: String,
    val get_time: Int,
    val gif_url: Any,
    val id: Int,
    val img_url: String,
    val income: String,
    val last_time: String,
    val machine_id: String,
    val name: String,
    val price: String,
    val machine_sum:String,
    val status: Int
)