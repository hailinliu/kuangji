package it.mbkj.personcenter.fragment

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.TranferActivity
import it.mbkj.personcenter.activity.WithDrawActivity

class WithDrawActivityPresenter:BasePresenter<WithDrawActivity>() {
    fun submit(session_id:String,wallet_address:String,sum:String,pay_pwd:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.my/withdraw_score")
            .params("session_id",session_id)
            .params("wallet_address",wallet_address)
            .params("sum",sum)
            .params("pay_pwd",pay_pwd)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as WithDrawActivity).execute(
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