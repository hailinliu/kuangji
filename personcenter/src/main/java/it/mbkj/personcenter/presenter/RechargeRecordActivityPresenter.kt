package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.RechargeActivity
import it.mbkj.personcenter.activity.RechargeRecordActivity
import it.mbkj.personcenter.bean.RechargeRecordBean

class RechargeRecordActivityPresenter:BasePresenter<RechargeRecordActivity>() {
    fun getList(session_id:String,page:Int){
        OkGo.post<BaseBean<RechargeRecordBean>>(HttpConfig.BASE_URL+"/v1.my/recharge_log")
            .params("session_id",session_id)
            .params("page",page)
            .execute(object : CallBackOption<BaseBean<RechargeRecordBean>>() {}.loadBind(mView as RechargeRecordActivity).execute(
                object : ILoadBind<BaseBean<RechargeRecordBean>> {
                    override fun excute(bean: BaseBean<RechargeRecordBean>) {
                        if(bean.code==200){
                            bean.data.list?.let{
                                (mView as RechargeRecordActivity).setUI(bean.data.list)
                            }

                        }
                    }
                }
            )
            )
    }
}