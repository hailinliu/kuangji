package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.TransferRecordActivity
import it.mbkj.personcenter.bean.TranferRecordBean

class TransferRecordActivityPresenter:BasePresenter<TransferRecordActivity>() {
    fun getList(session_id:String,page:Int){
        OkGo.post<BaseBean<TranferRecordBean>>(HttpConfig.BASE_URL+"/v1.my/transfer_log")
            .params("session_id",session_id)
            .params("page",page)
            .execute(object : CallBackOption<BaseBean<TranferRecordBean>>() {}.loadBind(mView as TransferRecordActivity).execute(
                object : ILoadBind<BaseBean<TranferRecordBean>> {
                    override fun excute(bean: BaseBean<TranferRecordBean>) {
                        if(bean.code==200){
                            bean.data.list?.let{
                                (mView as TransferRecordActivity).setUI(bean.data.list)
                            }

                        }
                    }
                }
            )
            )
    }
}