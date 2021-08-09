package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.UpdateLoginActivity

class UpdateLoginActivityPresenter:BasePresenter<UpdateLoginActivity>() {
    fun updataLoginPwd(session_id:String,old_password:String,password:String,password_confirm:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.my/update_pwd")
            .params("session_id",session_id)
            .params("password",password)
            .params("old_password",old_password)
            .params("password_confirm",password_confirm)

            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as UpdateLoginActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            ToastUtil.showShort(bean.msg)
                            (mView as UpdateLoginActivity).setUI(bean.msg)
                        }
                    }
                }
            ))

    }
}