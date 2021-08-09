package it.mbkj.personcenter.bean

data class HomePageBean(

    val machine_list: List<Machine>,
    val user_msg: UserMsg
)

data class Machine(
    val id: String,
    val name: String
)

data class UserMsg(
    val head_img: String,
    val level: Int,
    val level_msg: LevelMsg,
    val money: String,
    val phone: String,
    val score: String
)

data class LevelMsg(
    val img_str: String,
    val name: String
)