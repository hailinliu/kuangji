package it.mbkj.personcenter.bean

data class QuesonBean(
    val list: List<Demo1>
)

data class Demo1(
    val answer: String,
    val answer_time: String,
    val id: Int,
    val question: String,
    val question_time: String,
    val user_id: String
)