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
import it.mbkj.personcenter.activity.MyTeamActivity
import it.mbkj.personcenter.bean.MyTeamBean


class MyTeamActivityPresenter:BasePresenter<MyTeamActivity>() {
    fun getTeam(session_id:String){
        OkGo.post<BaseBean<MyTeamBean>>(HttpConfig.BASE_URL+"/v1.my/my_team")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<MyTeamBean>>() {
            }.loadBind(mView as MyTeamActivity).execute(
            object : ILoadBind<BaseBean<MyTeamBean>> {
                override fun excute(bean: BaseBean<MyTeamBean>) {
                     if(bean.error_code==20000){
                         ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                     }else if(bean.error_code==1001){
                         ToastUtil.showShort(bean.msg)
                     }
                    if(bean.code==200){
                        (mView as MyTeamActivity).setUI(bean.data)
                    }
                }
            }
        ))
       /* OkGo.post<String>(HttpConfig.BASE_URL+"/v1.my/my_team")
                .params("session_id",session_id)
                .execute(object :StringCallback(){
                    override fun onSuccess(response: Response<String>?) {
                        response!!.body()
                    }

                })*/
    }
}