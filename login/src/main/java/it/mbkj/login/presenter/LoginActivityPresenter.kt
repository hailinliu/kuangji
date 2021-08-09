package it.mbkj.login.presenter

import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.sskj.common.util.GSonUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.IBaseView
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.login.activity.LoginActivity
import it.mbkj.login.bean.LoginBean


class LoginActivityPresenter:BasePresenter<LoginActivity>() {
    fun login(phone:String,password:String){
        OkGo.post<String>(HttpConfig.BASE_URL+"/v1.login/login")
            .params("phone",phone)
            .params("password",password)
            .execute(object :StringCallback(){
                override fun onSuccess(s: Response<String>?) {
                    var bean=  GSonUtil.GsonToBean(s!!.body(),LoginBean::class.java)
                    (mView as LoginActivity).setData(bean)
                   //ToastUtil.showShort("s:"+bean.error_code)
                }

                override fun onError(response: Response<String>?) {
                    response!!.body()
                }
            }

            )

    }
}



