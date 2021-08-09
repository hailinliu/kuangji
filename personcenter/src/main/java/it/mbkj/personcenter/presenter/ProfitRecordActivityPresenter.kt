package it.mbkj.personcenter.presenter

import com.alibaba.android.arouter.launcher.ARouter
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig

import it.mbkj.personcenter.activity.ProfitRecordActivity
import it.mbkj.personcenter.bean.PledgeListBean
import it.mbkj.personcenter.bean.ProfitRecordBean

class ProfitRecordActivityPresenter:BasePresenter<ProfitRecordActivity>() {
    fun getProfitList(session_id:String,page:Int){
        OkGo.post<BaseBean<ProfitRecordBean>>(HttpConfig.BASE_URL+"/v1.my/money_log")
            .params("session_id",session_id)
            .params("page",page)
            .execute(object : CallBackOption<BaseBean<ProfitRecordBean>>() {}.loadBind(mView as ProfitRecordActivity).execute(
                object : ILoadBind<BaseBean<ProfitRecordBean>> {
                    override fun excute(bean: BaseBean<ProfitRecordBean>) {
                         if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }
                        if(bean.code==200){
                            (mView as ProfitRecordActivity).setUI(bean.data.list)
                        }
                    }
                }
            ))
       /* OkGo.post<String>(HttpConfig.BASE_URL+"/v1.my/money_log")
            .params("session_id",session_id)
            .execute(object :StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                    response!!.body()
                }

            })*/
    }
}