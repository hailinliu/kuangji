package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.TranferActivity

class TranferActivityPresenter:BasePresenter<TranferActivity>() {
    fun transfer(session_id:String,phone:String,sum:String,pay_pwd:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.my/wallet_transfer")
            .params("session_id",session_id)
            .params("phone",phone)
            .params("sum",sum)
            .params("pay_pwd",pay_pwd)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as TranferActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        if(bean.code==200){
                          ToastUtil.showShort(bean.msg)

                        }
                    }
                }
            ))
    }
}