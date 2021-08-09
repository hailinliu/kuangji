package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.TransferRecordActivity
import it.mbkj.personcenter.activity.WithDrawRecordActivity
import it.mbkj.personcenter.bean.TiBiRecordBean
import it.mbkj.personcenter.bean.TranferRecordBean

class WithDrawRecordActivityPresenter:BasePresenter<WithDrawRecordActivity>() {
    fun getList(session_id:String,page:Int){
        OkGo.post<BaseBean<TiBiRecordBean>>(HttpConfig.BASE_URL+"/v1.my/withdraw_log")
            .params("session_id",session_id)
            .params("page",page)
            .execute(object : CallBackOption<BaseBean<TiBiRecordBean>>() {}.loadBind(mView as WithDrawRecordActivity).execute(
                object : ILoadBind<BaseBean<TiBiRecordBean>> {
                    override fun excute(bean: BaseBean<TiBiRecordBean>) {
                        if(bean.code==200){
                            bean.data.list?.let{
                                (mView as WithDrawRecordActivity).setUI(bean.data)
                            }

                        }
                    }
                }
            )
            )
    }

}


