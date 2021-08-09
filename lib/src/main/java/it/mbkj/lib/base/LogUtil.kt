package it.mbkj.lib.base

import com.orhanobut.logger.*

class LogUtil {
    companion object{
        fun init(isLogEnable: Boolean){
            val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) // 是否显示线程信息，默认为ture
                .methodCount(3) // 显示的方法行数，默认为2
                .tag("okgo") // 每个日志的全局标记。默认PRETTY_LOGGER
                .build()

            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
            Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.DEBUG
                }
            })
            fun d(message: String?) {
                Logger.d(message)
            }

            fun i(message: String?) {
                Logger.i(message!!, *arrayOfNulls(0))
            }

            fun w(message: String, e: Throwable?) {
                val info = e?.toString() ?: "null"
                Logger.w("$message：$info", *arrayOfNulls(0))
            }

            fun e(message: String?, e: Throwable?) {
                Logger.e(e, message!!, *arrayOfNulls(0))
            }

            fun json(json: String?) {
                Logger.json(json)
            }
        }

    }
}