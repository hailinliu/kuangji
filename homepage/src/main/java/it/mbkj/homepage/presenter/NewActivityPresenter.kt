package it.mbkj.homepage.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.homepage.activity.NewActivity
import it.mbkj.homepage.bean.HomePageBean
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig

class NewActivityPresenter:BasePresenter<NewActivity>() {
    fun get(session_id:String,id:String){
OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.machine/get_machine_income")
    .params("session_id",session_id)
    .params("id",id)
    .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as NewActivity).execute(
        object : ILoadBind<BaseBean<String>> {
            override fun excute(bean: BaseBean<String>) {
                /* if(bean.error_code==20000){
                     ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                 }else*/ if(bean.code==200){
                   ToastUtil.showShort(bean.msg)
                }
            }
        }
    ))
    }
}