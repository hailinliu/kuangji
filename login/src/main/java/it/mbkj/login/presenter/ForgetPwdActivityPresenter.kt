package it.mbkj.login.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.login.activity.ForgetPwdActivity
import it.mbkj.login.activity.RegisterActivity
import it.mbkj.login.bean.LoginBean

class ForgetPwdActivityPresenter:BasePresenter<ForgetPwdActivity>() {
    fun getCode(phone: String){
        OkGo.post<LoginBean>(HttpConfig.BASE_URL + "/v1.login/forget_send")
            .params("phone", phone)
            .execute(object : CallBackOption<LoginBean>() {}
                .loadBind(mView as ForgetPwdActivity)
                .execute(object : ILoadBind<LoginBean> {
                    override fun excute(bean: LoginBean) {
                        (mView as ForgetPwdActivity).setData(bean)
                    }
                })
            )
    }
    fun forgetPwd(phone: String,password:String,password_confirm:String,code:String){
        OkGo.post<LoginBean>(HttpConfig.BASE_URL + "/v1.login/forget_pwd")
            .params("phone", phone)
            .params("password",password)
            .params("password_confirm",password_confirm)
            .params("code",code)
            .execute(object : CallBackOption<LoginBean>() {}
                .loadBind(mView as ForgetPwdActivity)
                .execute(object : ILoadBind<LoginBean> {
                    override fun excute(bean: LoginBean) {
                        (mView as ForgetPwdActivity).setUI(bean)
                    }
                })
            )
    }
}