package it.mbkj.kuangji.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class NewTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    init {
        val tf= Typeface.createFromAsset(context.assets,"fonts/ss.TTF")
        this.typeface=tf
    }
}