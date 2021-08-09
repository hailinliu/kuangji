package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.ConactUsActivity
import it.mbkj.personcenter.activity.RechargeActivity
import it.mbkj.personcenter.bean.QuesonBean
import it.mbkj.personcenter.bean.RechargeBean

class ConactUsActivityPresenter:BasePresenter<ConactUsActivity>() {
    fun submit(session_id:String,question:String){
    OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.my/up_question")
        .params("session_id",session_id)
        .params("question",question)
        .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as ConactUsActivity).execute(
            object : ILoadBind<BaseBean<String>> {
                override fun excute(bean: BaseBean<String>) {
                    if(bean.code==200){
                        ToastUtil.showShort(bean.msg)
                        (mView as ConactUsActivity).setData()
                    }
                }
            }
        ))
    }
    fun getRecord(session_id:String,page:Int){
        OkGo.post<BaseBean<QuesonBean>>(HttpConfig.BASE_URL+"/v1.my/question_list")
            .params("session_id",session_id)
            .params("page",page)
            .execute(object : CallBackOption<BaseBean<QuesonBean>>() {}.loadBind(mView as ConactUsActivity).execute(
                object : ILoadBind<BaseBean<QuesonBean>> {
                    override fun excute(bean: BaseBean<QuesonBean>) {
                        if(bean.code==200){
                            (mView as ConactUsActivity).setUI(bean.data)
                        }
                    }
                }
            ))
    }
}