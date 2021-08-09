package it.mbkj.tradecenter.bean

data class KChartBean(
    val all_deal: Int,
    val code: Int,
    val data: Data,
    val price: String,
    val waite_deal: Int
)

data class Data(
    val k_line_time: List<String>,
    val k_line_value: List<String>
)