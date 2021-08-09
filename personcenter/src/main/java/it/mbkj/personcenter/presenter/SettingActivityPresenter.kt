package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.SettingActivity
import it.mbkj.personcenter.activity.UpdateDealPasswordActivity

class SettingActivityPresenter:BasePresenter<SettingActivity>() {
    fun getCode(session_id:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.my/forget_send")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as UpdateDealPasswordActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            ToastUtil.showShort(bean.msg)
                            //(mView as UpdateDealPasswordActivity).setUI(bean.data)
                        }
                    }
                }
            ))
    }
}