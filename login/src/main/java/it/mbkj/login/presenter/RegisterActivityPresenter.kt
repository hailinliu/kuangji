package it.mbkj.login.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.login.activity.RegisterActivity
import it.mbkj.login.bean.LoginBean

class RegisterActivityPresenter:BasePresenter<RegisterActivity>() {
    fun getCode(phone: String){
        OkGo.post<LoginBean>(HttpConfig.BASE_URL + "/v1.login/forget_send")
            .params("phone", phone)
            .execute(object : CallBackOption<LoginBean>() {}
                .loadBind(mView as RegisterActivity)
                .execute(object : ILoadBind<LoginBean> {
                    override fun excute(bean: LoginBean) {
                        (mView as RegisterActivity).setData(bean)
                    }
                }) /*  new CallBackOption<BaseBean<LoginBean>>() {}
                        .loadBind(mView)
                        .execute(httpData -> {
                            if (httpData.getData().getGoogleState() == 0) {
                                mView.loginSuccess(httpData.getData());
                            } else {
                                mView.googleCheck(httpData.getData());
                            }
                        })*/
            )
    }
}