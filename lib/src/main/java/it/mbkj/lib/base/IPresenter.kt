package it.mbkj.lib.base

import android.app.Activity
import android.content.Context
import com.lzy.okgo.OkGo
import java.lang.ref.WeakReference

@Suppress("UNCHECKED_CAST")
open class IPresenter<VIEW:IBaseView> {
     @JvmField
     var mView:Any?=null
     private var mReference:WeakReference<Any>?=null
    var mContext: Activity?=null
    val context:Context?
                get() = mContext
    open fun attachView(view:Any?,context: Activity?){
        mReference = WeakReference(view as VIEW)
        mView = mReference!!.get()
        mContext = context
    }
        fun showLoading(){
                if(mContext!! is BaseActivity<*>){
                    (mContext as BaseActivity<*>).showLoading()
            }
        }
    fun hideLoading(){
        if(mContext is BaseActivity<*>){
            (mContext as BaseActivity<*>).hideLoading()
        }
    }
    fun detachView(){
        mReference!!.clear()
    }
    fun cancelRequest(){
        OkGo.getInstance().cancelTag(this)
    }
}