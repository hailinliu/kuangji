package it.mbkj.lib.base

import android.graphics.drawable.Drawable
import android.widget.TextView

interface IBaseView {
    fun isEmpty(textView: TextView?):Boolean
    fun getText(textView: TextView?):String?
    fun color(id:Int):Int
    fun drawable(id:Int):Drawable?
    fun setText(textView: TextView?, text: String?)
    fun showLoading()
    fun hideLoading()
}