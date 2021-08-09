package it.mbkj.lib.utils

class Utils {

    companion object{
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private val MIN_CLICK_DELAY_TIME = 1000
        private var lastClickTime: Long = 0
        fun isFastClick(): Boolean {
            var flag = false
            val curClickTime = System.currentTimeMillis()
            if (curClickTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
                flag = true
            }
            lastClickTime = curClickTime
            return flag
        }
    }

}