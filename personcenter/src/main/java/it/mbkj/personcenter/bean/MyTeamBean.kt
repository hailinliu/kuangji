package it.mbkj.personcenter.bean

    data class MyTeamBean(

    val list1: List<Demo>,
    val list2: List<Demo>,
    val team_machine_sum: String,
    val team_sum: Int
)

data class Demo(
    val ceng: Int,
    val create_time: String,
    val id: Int,
    val level: String,
    val level_img: String,
    val machine_name: String,
    val phone: String,
    val team_machine_sum: String
)



/*(

    val list: List<Demo>,
    val team_machine_sum: Int,
    val team_sum: Int
)

data class Demo(
    val ceng: Int,
    val create_time: String,
    val id: Int,
    val level: String,
    val level_img: String,
    val phone: String,
    val team_machine_sum: Int
)*/