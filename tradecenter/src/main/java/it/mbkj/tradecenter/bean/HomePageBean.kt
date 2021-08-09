package it.mbkj.tradecenter.bean

data class HomePageBean(

    val user_msg: UserMsg
)

data class UserMsg(
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