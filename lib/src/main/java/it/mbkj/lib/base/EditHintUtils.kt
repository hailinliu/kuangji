package it.mbkj.lib.base

import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan

object EditHintUtils {
    fun setHintSizeAndContent(content: String?, size: Int, bs: Boolean): SpannedString {
        val ss = SpannableString(content)
        // 新建一个属性对象,设置文字的大小
        val ass = AbsoluteSizeSpan(size, bs)
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return SpannedString(ss)
    }
}