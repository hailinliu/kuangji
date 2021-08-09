package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.UpdateDealPasswordActivity


class UpdateDealPasswordActivityPresenter:BasePresenter<UpdateDealPasswordActivity>() {
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

                        (mView as UpdateDealPasswordActivity).setUI()
                    }
                }
            }
        ))
    }
    fun updatePwd(session_id:String,password:String,Password_confirm:String,code:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.my/forget_pay_pwd")
            .params("session_id",session_id)
            .params("password",password)
            .params("password_confirm",Password_confirm)
            .params("code",code)
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
                            (mView as UpdateDealPasswordActivity).setdata(bean.msg)
                        }
                    }
                }
            ))
    }
}