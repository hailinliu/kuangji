package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.RechargeActivity
import it.mbkj.personcenter.bean.RechargeBean

class RechargeActivityPresenter:BasePresenter<RechargeActivity>(){
    fun getAddress(session_id:String){
        OkGo.post<BaseBean<RechargeBean>>(HttpConfig.BASE_URL+"/v1.my/wallet_address")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<RechargeBean>>() {}.loadBind(mView as RechargeActivity).execute(
                object : ILoadBind<BaseBean<RechargeBean>> {
                    override fun excute(bean: BaseBean<RechargeBean>) {
                        if(bean.code==200){
                            (mView as RechargeActivity).setUI(bean.data)
                        }
                    }
                }
            ))
    }
}